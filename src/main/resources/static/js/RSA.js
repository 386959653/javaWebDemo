// RSA, a suite of routines for performing RSA public-key computations in
// JavaScript.
//
// Requires BigInt.js and Barrett.js.
//
// Copyright 1998-2005 David Shapiro.
//
// You may use, re-use, abuse, copy, and modify this code to your liking, but
// please keep this header.
//
// Thanks!
// 
// Dave Shapiro
// dave@ohdave.com 

function RSAKeyPair(encryptionExponent, decryptionExponent, modulus) {
    this.e = biFromHex(encryptionExponent);
    this.d = biFromHex(decryptionExponent);
    this.m = biFromHex(modulus);
    // We can do two bytes per digit, so
    // chunkSize = 2 * (number of digits in modulus - 1).
    // Since biHighIndex returns the high index, not the number of digits, 1 has
    // already been subtracted.
    this.chunkSize = 2 * biHighIndex(this.m);
    this.radix = 16;
    this.barrett = new BarrettMu(this.m);
}

function twoDigit(n) {
    return (n < 10 ? "0" : "") + String(n);
}

function encryptedString(key, s)
// Altered by Rob Saunders (rob@robsaunders.net). New routine pads the
// string after it has been converted to an array. This fixes an
// incompatibility with Flash MX's ActionScript.
{
    var a = new Array();
    var sl = s.length;
    var i = 0;
    while (i < sl) {
        a[i] = s.charCodeAt(sl - i - 1);
        i++;
    }

    while (a.length % key.chunkSize != 0) {
        a[i++] = 0;
    }

    var al = a.length;
    var result = "";
    var j, k, block;
    for (i = 0; i < al; i += key.chunkSize) {
        block = new BigInt();
        j = 0;
        for (k = i; k < i + key.chunkSize; ++j) {
            block.digits[j] = a[k++];
            block.digits[j] += a[k++] << 8;
        }
        var crypt = key.barrett.powMod(block, key.e);
        var text = key.radix == 16 ? biToHex(crypt) : biToString(crypt, key.radix);
        result += text + " ";
    }
    return result.substring(0, result.length - 1); // Remove last space.
}

function decryptedString(key, s) {
    var blocks = s.split(" ");
    var result = "";
    var i, j, block;
    for (i = 0; i < blocks.length; ++i) {
        var bi;
        if (key.radix == 16) {
            bi = biFromHex(blocks[i]);
        }
        else {
            bi = biFromString(blocks[i], key.radix);
        }
        block = key.barrett.powMod(bi, key.d);
        for (j = 0; j <= biHighIndex(block); ++j) {
            result += String.fromCharCode(block.digits[j] & 255,
                block.digits[j] >> 8);
        }
    }
    // Remove trailing null, if any.
    if (result.charCodeAt(result.length - 1) == 0) {
        result = result.substring(0, result.length - 1);
    }
    return result;
}

//add by Chentf 2012.9.6 start
//如果改了 密匙对 要在这里改相应的系数
function getKey() {
    setMaxDigits(130);
    //第一个参数为加密指数、第二个参数为解密参数、第三个参数为公钥
    key = new RSAKeyPair("10001", "", "9c5026e54e78fa5c550057b1913e32122302ca8f303f11592d2b40eb245dbe6ef1afb9d11342b0bedfd2d8bcdb431b9467916822b64560d9211a4d018c15370fe7c3c281b53a2320b1aa0c3e07b6f4b347e24b9a00afa5b94e6544e3471a7ef6fc52fd0c4a4a0a6fbc52a287158affbfca6c4abbe2047c65e41dd8e7d8e3d7cf");
    return key
}

//end Chentf
