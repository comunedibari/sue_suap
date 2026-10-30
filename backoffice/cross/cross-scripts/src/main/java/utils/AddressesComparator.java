package utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class AddressesComparator {

	private static final Map<String, String> SYNONYMUMS= new HashMap<>();


	private static final String PREFIX_REGEX = "^(?:viale|via|vico|corso|traversa|strada)";
	private static final String SUFFIX_ADDRESS_NUMBER_REGEX = "[,]?\\s+(?:nr[\\.]?|n[\\.]?)?\\s*\\d+(?:bis|[\\\\\\/][a-zA-Z])?\\s*$";
	private static final String NUMBER_REGEX = "(\\d+)[\\.,](\\d+)";
	private static final String NO_ADDRESS_NUMBER_REGEX = "^(?:statale|provinciale)";

	private static final String TO_EMPTY_CHARACTER_REGEX = "[^\\w\\s]";
	private static final String TO_SPACE_CHARACTER_REGEX = "[-\\.°]";

	public AddressesComparator() {

		Path path = Paths.get("src", "main", "resources", "addresses", "synonyms.txt");
		String synonymsFile;
		try {
			synonymsFile = new String(Files.readAllBytes(path));
			List<String> sysnonyms = new ArrayList<>();
			String[] split = synonymsFile.split("\n");
			for(int i = 0; i < split.length; i++) {
				String cur = split[i].trim();
				if(cur.length() > 0 && !cur.startsWith("#"))
					sysnonyms.add(cur);
			}
			for(String row: sysnonyms) {
				String[] parts = row.split("=");
				String[] syns = parts[1].split(",\\s*");
				for(int i = 0; i<syns.length;i++) {
					String syn = syns[i].trim().toLowerCase();
					SYNONYMUMS.put(syn, parts[0].toLowerCase()+(syn.endsWith(".")?"-":""));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Compare two addresses (order matters)
	 * @param addressDb		address read by database
	 * @param addressXml	address read by xml
	 * @return
	 */
	public boolean compare(String addressDb, String addressXml) {
		String cleanAddressDb = cleanString(addressDb);
		String cleanAddressXml= cleanString(addressXml);
		boolean equal = false;
		
		if(!cleanAddressDb.isEmpty() && cleanAddressXml.isEmpty())
			equal = true;
		else if(cleanAddressDb.isEmpty() && !cleanAddressXml.isEmpty())
			equal = false;
		else
			equal = comparePrivate(cleanAddressDb, cleanAddressXml);
		
		return equal;
	}

	private String cleanString(String address) {
		String addressMod = address.toLowerCase();

		for(Entry<String, String> syn : SYNONYMUMS.entrySet()) 
			addressMod = addressMod.replaceAll(syn.getKey(), syn.getValue());

		addressMod = addressMod.replaceAll(PREFIX_REGEX, "").trim();
		addressMod = addressMod.replaceAll(NUMBER_REGEX, "$1_$2").trim();
		if(!Pattern.compile(NO_ADDRESS_NUMBER_REGEX).matcher(addressMod).find())
			addressMod = addressMod.replaceAll(SUFFIX_ADDRESS_NUMBER_REGEX, "").trim();
		addressMod = addressMod.replaceAll(TO_SPACE_CHARACTER_REGEX, " ");
		addressMod = addressMod.replaceAll(TO_EMPTY_CHARACTER_REGEX, "");

		return addressMod.trim();
	}

	private boolean comparePrivate(String address1, String address2) {
		List<String> tokens1 = Arrays.asList(address1.split("\\s+"));
		List<String> tokens2 = Arrays.asList(address2.split("\\s+"));

		boolean equal = false;
		int iPerMin = 0;
		int iPerMax = 0;
		List<String> smallerList = null;
		if(!tokens1.isEmpty() && !tokens2.isEmpty()) {
			equal = true;
			List<String> greaterList = tokens1.size()>tokens2.size()?tokens1:tokens2;
			smallerList = tokens1.size()<=tokens2.size()?tokens1:tokens2;

			while(iPerMin<smallerList.size() && iPerMax<greaterList.size() && equal) {

				boolean tokensEqual = false;

				while(!tokensEqual && iPerMin<smallerList.size() && iPerMax<greaterList.size()) {
					String tokenG = getToken(greaterList, iPerMax);
					String tokenM = getToken(smallerList, iPerMin);

					if(tokenG != null && tokenM != null) {
						tokensEqual = tokenG.equalsIgnoreCase(tokenM);
						//check that one of tokens can be an abbreviation
						if(!tokensEqual && (tokenG.length() < 4 || tokenM.length() < 4)) 
							tokensEqual = compareStringByChar(tokenG, tokenM);
					}
					if(!tokensEqual)
						iPerMax++;
				}
				equal &= tokensEqual;
				iPerMin++;
				iPerMax++;
			}
		}
		//to be correct smallerList have to be read at all
		return equal && smallerList != null && iPerMin == smallerList.size();
	}

	private String getToken(List<String> tokens, int i) {
		String ret = null;
		try {
			ret = tokens.get(tokens.size()-i-1);
		}catch(Exception e) {
			ret = null;
		}
		return ret;
	}

	private boolean compareStringByChar(String token1, String token2) {
		return token1.startsWith(token2) || token2.startsWith(token1);
	}
}
