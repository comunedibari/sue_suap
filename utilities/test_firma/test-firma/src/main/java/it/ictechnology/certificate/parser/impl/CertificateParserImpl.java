package it.ictechnology.certificate.parser.impl;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import it.ictechnology.certificate.parser.CertificateParser;

public class CertificateParserImpl implements CertificateParser {
	private static final Logger logger = LoggerFactory.getLogger(CertificateParserImpl.class);

	private Program program;

	private class Interpreter {
		private final Program program;
		private final X509Certificate certificate;
		private final Object datiUtente;
		private Program.Format format;
		private final Map<String, String> certData = new HashMap();

		public Interpreter(Program program, X509Certificate certificate, Object datiUtente) {
			if (program == null)
				throw new IllegalStateException("program not configured");
			if (program.getDetection() == null)
				throw new IllegalStateException("Illegal program, detection is null");
			if (program.getFormats() == null)
				throw new IllegalStateException("Illegal program, formats is null");
			if (certificate == null)
				throw new NullPointerException("Missing certificate");
			if (datiUtente == null) {
				throw new NullPointerException("Missing datiUtente");
			}
			this.program = program;
			this.certificate = certificate;
			this.datiUtente = datiUtente;

			formatDetection();

			parseCertificateAttributes();
			logger.info("certData" + "\n");
			for (String key : this.certData.keySet()) {
				String value = this.certData.get(key);
				logger.info(key + " " + value);
			}
			applyFormat();
		}

		private void formatDetection() {
			for (Iterator i$ = this.program.getDetection().iterator(); i$.hasNext();) {
				Program.TypeDetection detection = (Program.TypeDetection) i$.next();

				String formatName = detection.resolveFormatName(this.certificate);

				if (formatName != null) {
					this.format = ((Program.Format) this.program.getFormats().get(formatName));

					if (this.format == null) {
						throw new IllegalStateException("Invalid program, detected format is not defined: formatName="
								+ formatName + ", issuer=" + this.certificate.getIssuerDN().getName());
					}
					if (this.format.getProperties() == null) {
						throw new IllegalStateException(
								"Invalid program, format has no properties: formatName=" + formatName);
					}
					if (CertificateParserImpl.logger.isDebugEnabled()) {
						CertificateParserImpl.logger.debug("Format found: " + formatName);
					}
					return;
				}
			}

			throw new RuntimeException(
					"Unknown certificate type, parsing failed: issuer=" + this.certificate.getIssuerDN().getName());
		}

		private void applyFormat() {
			BeanWrapper wrapper = CertificateParserImpl.this.newBeanWrapper(this.datiUtente);
			logger.info("certificate-descriptions.xml"); 
			for (Iterator i$ = this.format.getProperties().iterator(); i$.hasNext();) {
				Program.Property property = (Program.Property) i$.next();

				String javaProp = property.getJavaProperty();
//				logger.info(property.getAttribute());
				if (wrapper.getPropertyValue(javaProp) == null) {
					logger.debug("javaProp: " + javaProp + " property.getAttribute(): " + property.getAttribute());
					String value = (String) this.certData.get(property.getAttribute());

					Pattern matcher = property.getMatcher();
					logger.debug("value: " + value + " matcher: " + matcher);
					if (matcher != null) {
						Matcher m = matcher.matcher(value);

						m.find();
						logger.debug("m: " + m + " property.getGroupSelection(): " + property.getGroupSelection());
						value = m.group(property.getGroupSelection());
					}

					wrapper.setPropertyValue(javaProp, value);
					logger.info("TROVATO: " + javaProp + "    -    " + property.getAttribute() + "    -    " + value);
				}
			}
		}

		private void parseCertificateAttributes() {
			parseAttribute(this.certificate.getIssuerDN().getName(), "issuer");
			parseAttribute(this.certificate.getSubjectDN().getName(), "subject");
		}

		private void parseAttribute(String txt, String attrName) {
			this.certData.put(attrName, txt);

			int i = 0;
			int l = txt.length();

			while (i < l) {
				int j = txt.indexOf('=', i);

				if (j == -1) {
					break;
				}
				String key = txt.substring(i, j).trim();

				j++;
				int k;
				if (txt.charAt(j + 1) == '"') {

					k = txt.indexOf('"', j);

					if (k == -1) {
						break;
					}
					k++;

				} else {

					k = txt.indexOf(',', j);

					if (k == -1) {
						k = l;
					}
				}
				i = k + 1;

				String value = txt.substring(j, k);

				this.certData.put(attrName + '.' + key, value);
			}
		}
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public void parse(X509Certificate certificate, Object utente) {
		if (utente == null) {
			throw new IllegalArgumentException("L'oggetto utente deve essere istanziato");
		}
		new Interpreter(getProgram(), certificate, utente);
	}

	protected BeanWrapper newBeanWrapper(Object datiUtente) {
		return new BeanWrapperImpl(datiUtente);
	}
}