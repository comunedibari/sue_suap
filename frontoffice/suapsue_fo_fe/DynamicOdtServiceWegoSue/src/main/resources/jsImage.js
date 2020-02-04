<script>
/*
 This file and its contents and techniques (excepting the notion of run-length encoding)
 are (C)CopyLeft Benn Herrera August 2004.
 benn@panscopic.com
 You may use the techniques and code found herein but you must accompany its
 usage by this notice.  This code is presented "as-is" without any statement
 as to its fitness for a particular purpose or absence of potential for harm.
 see http://www.gnu.org/copyleft/gpl.html for details.
 */

// get the complete, cascaded style that is currently in effect for a given element
//(as opposed to only the properties directly set by the element's class and/or style attributes)
function getEffectiveStyle( elem ) {
    if ( elem.currentStyle )
        return elem.currentStyle;
    return window.getComputedStyle( elem, null );
}

var BASE64_INVERSE = null;

var HEX_DIGITS = "0123456789ABCDEF";

function initBase64Inverse() {
    BASE64_INVERSE = new Array();

    var base64Alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                      "abcdefghijklmnopqrstuvwxyz" +
                      "0123456789"                 +
                      "+/=";

    for ( var i = 0; i < base64Alpha.length; i++ ) {
        BASE64_INVERSE[base64Alpha.charCodeAt(i)] = i;
    }

    BASE64_INVERSE['='.charCodeAt(0)] = 0;
}

function base64Decode( data ) {
    if ( data == null )
        return null;

    if ( BASE64_INVERSE == null )
        initBase64Inverse();

    var out      = new Array();
    var packed24 = 0;

    for ( var i = 0, j = 0; i < data.length; i += 4, j += 3 ) {
        packed24 = (BASE64_INVERSE[data.charCodeAt(i)]   << 18)|(BASE64_INVERSE[data.charCodeAt(i+1)] << 12)|
                   (BASE64_INVERSE[data.charCodeAt(i+2)] << 6 )|BASE64_INVERSE[data.charCodeAt(i+3)];

        out[j]   =  packed24 >> 16;
        out[j+1] = (packed24 >> 8) & 255;
        out[j+2] =  packed24       & 255;
    }

    return out;
}

//
// take the rle encoded data, create a singled celled table holding
// each row of pixels as a 1 row table.
// the dom structure created here has been chosen after much testing
// and tweaking.  It works for IE, Mozilla/Netscape/Firefox and is
// fast on all of them.  If this image has already been decoded
// then the dom structure it decoded to is cloned into the container.
//
function displayJSImage( container, printable ) {
    // if we've already unpacked this image, re-use our previous work.
    if ( this.decodedImage != null ) {
        if ( this.decodedImage.parentNode == null ) {
            container.appendChild( this.decodedImage );
        } else {
            container.appendChild( this.decodedImage.cloneNode( true ) );
        }

        return;
    }

    // the table that contains the entire image
    var imgTable           = document.createElement( "table" );
    imgTable.cols          = 1;
    imgTable.height        = this.pixSize * this.height;
    imgTable.width         = this.pixSize * this.width;
    imgTable.cellPadding   = 0;
    imgTable.cellSpacing   = 0;
    imgTable.align         = getEffectiveStyle( container ).textAlign;

    // the tbody (required for IE or nothing will display) for the table
    var imgTbody           = document.createElement( "tbody" );
    imgTable.appendChild( imgTbody );

    // the one row of the 1x1 image table
    var imgRow             = document.createElement( "tr" );
    imgTbody.appendChild( imgRow );

    // the one cell of the 1 row by 1 cell image table
    var imgCell            = document.createElement( "td" );
    imgCell.style.fontSize = 0;
    imgCell.style.width    = this.width * this.pixSize;
    imgCell.style.height   = this.pixSize;
    imgRow.appendChild( imgCell );

    // the table to repeatedly clone for each row
    var rowTable           = document.createElement( "table" );
    rowTable.width         = this.pixSize * this.width;
    rowTable.cellPadding   = 0;
    rowTable.cellSpacing   = 0;
    
    // the tbody (required for IE or nothing will display) for the table
    var rowTbody           = document.createElement( "tbody" );
    rowTable.appendChild( rowTbody );

    // the single row for our Width by 1 row table
    var rowRow             = document.createElement( "tr" );
    rowTbody.appendChild( rowRow );

    // the cell that will be cloned for each pixel or pixel run
    var pixCell            = document.createElement( "td" );

    // if the image has to be printable, use divs in cells with border-colors set.
    // this takes longer to unpack and to render, but will show up by default in printout.
    if ( printable ) {
        // the div with the border colors that will make our image visible
        var pixDiv             = document.createElement( "div" );
        pixCell.appendChild( pixDiv );
    
        pixDiv.style.lineHeight        = 0;
        pixDiv.style.fontSize          = 0;
        pixDiv.style.height            = 0;
        pixDiv.style.borderWidth       = 0;
        pixDiv.style.borderBottomWidth = this.pixSize;
        pixDiv.style.width             = this.pixSize;
        pixDiv.style.borderStyle       = "solid";
        
        for ( var y = 0, i = 0, x, run, row, pix, cols; y < this.height; y++ ) {
            // clone the row table for adding to the image cell
            row      = rowTable.cloneNode( true );
            row.cols = this.width;
            // get the row to which we directly add pixel cells
            rowRow = row.firstChild.firstChild;
            
            for ( x = 0, cols = 0; x < this.width; ) {
                run = this.data[i++];
    
                // high bit not set means run of identical values
                if ( run < 128 ) {
                    pix = pixCell.cloneNode( true );
          
                    // 0 - 127 maps to 2 - 129. same-value runs of length 0 & 1 make no sense.
                    run                                    += 2;                
                    pix.firstChild.style.borderBottomColor  = this.palette[this.data[i++]];
                    pix.firstChild.style.width              = run * this.pixSize;
                    x                                      += run;
                    cols++;
                    
                    rowRow.appendChild(  pix );
                    
                    continue;
                }
    
                // 128 - 255 map to 1-128 - runs of disparate values, runs of length 0 have no meaning
                for ( run -= 127, x += run, cols += run; run > 0; run-- ) {
                    pix = pixCell.cloneNode( true );
     
                    pix.firstChild.style.borderBottomColor = this.palette[this.data[i++]];
                    
                    rowRow.appendChild(  pix );
                }
            }
    
            // give the renderer a helping hand on how many cells we have
            row.cols = cols;
            
            // add the 1 row by Width table to the cell of the 1 row by 1 cell image table
            imgCell.appendChild( row );
        }
    // if printability is not required, use this faster method.
    } else {
        pixCell.style.fontSize         = 0;
        pixCell.style.width            = this.pixSize;
        pixCell.style.height           = this.pixSize;
    
        for ( var y = 0, i = 0, x, run, row, pix, cols; y < this.height; y++ ) {
            // clone the row table for adding to the image cell
            row      = rowTable.cloneNode( true );
            row.cols = this.width;
            // get the row to which we directly add pixel cells
            rowRow = row.firstChild.firstChild;
            
            for ( x = 0, cols = 0; x < this.width; ) {
                run = this.data[i++];
    
                // high bit not set means run of identical values
                if ( run < 128 ) {
                    pix = pixCell.cloneNode( false );
          
                    // 0 - 127 maps to 2 - 129. same-value runs of length 0 & 1 make no sense.
                    run                      += 2;
                    pix.style.backgroundColor = this.palette[this.data[i++]];
                    pix.style.width           = run * this.pixSize;
                    x                        += run;
                    cols++;
                    
                    rowRow.appendChild(  pix );
                    
                    continue;
                }
    
                // 128 - 255 map to 1-128 - runs of disparate values, runs of length 0 have no meaning
                for ( run -= 127, x += run, cols += run; run > 0; run-- ) {
                    pix = pixCell.cloneNode( false );
     
                    pix.style.backgroundColor = this.palette[this.data[i++]];
                    
                    rowRow.appendChild(  pix );
                }
            }
    
            // give the renderer a helping hand on how many cells we have
            row.cols = cols;
            
            // add the 1 row by Width table to the cell of the 1 row by 1 cell image table
            imgCell.appendChild( row );
        }
    }
    
    // save our decoded (domified) image for repeated use
    this.decodedImage = imgTable;

    // free up the memory from our rle data.
    this.data = null;

    // add the image dom to the document for display
    container.appendChild( this.decodedImage );
}

//
// take a printable string that encodes 6 bit rle and
// convert to an 8 bit rle array.
//
function rle6toRLE8() {
    var rle6 = this.data;
    var rle8 = new Array();

    if ( BASE64_INVERSE == null )
        initBase64Inverse();

    for ( var i = 0, v; i < rle6.length; ) {
        // subtract first printable non-space to offset to 0-based values
        v = BASE64_INVERSE[rle6.charCodeAt(i)];

        // if < 32, this is a run
        if ( v < 32 ) {
            // stash the run length
            rle8[i] = v;
            i++;
            // stash the repeated value
            rle8[i] = BASE64_INVERSE[rle6.charCodeAt(i)];
            i++;
            continue;
        }

        // if >= 32, this was a shamble.
        // offset so that subtracting 127
        // will give the correct shamble length
        rle8[i] = v + (128-32);

        // copy the shamble.
        for ( v -= 31, i++; v > 0; rle8[i] = BASE64_INVERSE[rle6.charCodeAt(i)], v--, i++ );
    }

    this.data = rle8;
}

//
// unpack our encoded rle8 data from our less compute-intense version of base64
//
function base64ToRLE8() {
    this.data = base64Decode( this.data );
}

//
// unpack our palette from our version of base64 and then
// convert to an array of html hex color strings.
//
function decodePalette() {
    var colors = base64Decode( this.palette );

    this.palette = new Array();

    for ( var i = 0, j = 0, r, g, b; i < colors.length; i += 3, j++ ) {
        r = HEX_DIGITS.charAt( colors[i  ] >> 4 ) + HEX_DIGITS.charAt( colors[i  ] & 15 );
        g = HEX_DIGITS.charAt( colors[i+1] >> 4 ) + HEX_DIGITS.charAt( colors[i+1] & 15 );
        b = HEX_DIGITS.charAt( colors[i+2] >> 4 ) + HEX_DIGITS.charAt( colors[i+2] & 15 );

        this.palette[j] = '#' + r + g + b;
    }
}

//
// generate a linear grayscale palette scaled to the appropriate size based on our bits/pixel
//
function generateMonoPalette() {
    this.palette = new Array();

    for ( var i = 0, paletteSize = Math.pow( 2, this.bitsPerPixel ), maxVal = paletteSize - 1.0, m; i < paletteSize; i++ ) {
        m = Math.floor( ((i/maxVal) * 255.0) + 0.5 );
        m = HEX_DIGITS.charAt( m >> 4 ) + HEX_DIGITS.charAt( m & 15 );

        this.palette[i] = '#' + m + m + m;
    }
}

function setScale( scale ) {
    var pixSize = parseInt( String(scale) );

    if ( pixSize == NaN || pixSize < 1 )
        pixSize = 1;

    this.pixSize = pixSize;
}

function getScale() {
    return this.pixSize;
}

//
// embedded javascript image constructor
//
function JSImage( fileName, width, height, bitsPerPixel, lowestUsed, highestUsed, mostUsed, data, palette ) {
    // methods
    this.display             = displayJSImage;
    this.setScale            = setScale;
    this.getScale            = getScale;
    this.base64ToRLE8        = base64ToRLE8;
    this.rle6toRLE8          = rle6toRLE8;
    this.decodePalette       = decodePalette;
    this.generateMonoPalette = generateMonoPalette;

    // members
    this.fileName         = fileName;
    this.width            = width;
    this.height           = height;
    this.pixSize          = 1;
    this.bitsPerPixel     = bitsPerPixel;
    // these are not currently used, but some enterprising person might be able
    // to get optimization mileage out of them in the future.
    this.lowestUsed       = lowestUsed; // lowest used sample value
    this.highestUsed      = highestUsed;// highest used sample value
    this.mostUsed         = mostUsed;   // most frequently used sample value

    this.data             = data;
    this.palette          = palette;
    this.isColor          = palette ? true : false;
    this.decodedImage     = null;

    switch( this.bitsPerPixel ) {
        case 6:  this.rle6toRLE8();   break;
        case 8:  this.base64ToRLE8(); break;
        default:
            this.decodedImage = document.createTextNode( "Unsupported Bits Per Pixel: " + bitsPerPixel );
            return;
    }

    if ( palette ) {
        this.decodePalette();
    } else {
        this.generateMonoPalette();
    }
}

//replaceDynamicJs

</script>