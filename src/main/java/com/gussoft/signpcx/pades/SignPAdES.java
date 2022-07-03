package com.gussoft.signpcx.pades;

import com.gussoft.signpcx.models.SignatureModel;
import com.gussoft.signpcx.models.SignatureParameters;
import com.gussoft.signpcx.utils.Utils;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignerTextHorizontalAlignment;
import eu.europa.esig.dss.enumerations.SignerTextPosition;
import eu.europa.esig.dss.enumerations.SignerTextVerticalAlignment;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.pades.*;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.SignatureTokenConnection;

import java.awt.*;
import java.io.File;
import java.util.List;

public class SignPAdES {

    private SignatureTokenConnection token;
    DSSPrivateKeyEntry privateKey;
    private final SignatureModel model;

    public SignPAdES(SignatureModel model){
        token =  Utils.getToken();
        List<DSSPrivateKeyEntry> keys = token.getKeys();
        try {
            privateKey = Utils.getSigner(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.model = model;
    }

    public DSSDocument getSignDocument(File fileIn, File fileOut, SignatureParameters signatureParameters) {
        DSSDocument fileToSign = new FileDocument(fileIn);
        DSSDocument fileSigned = new FileDocument(fileOut);
        try {

            PAdESSignatureParameters parameters = new PAdESSignatureParameters();
            parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
            parameters.setSigningCertificate(privateKey.getCertificate());
            parameters.setCertificateChain(privateKey.getCertificateChain());

            SignatureImageParameters imageParameters = new SignatureImageParameters();
            imageParameters.setImage(new FileDocument(model.getSignatureImagen()));

            SignatureFieldParameters fieldParameters = new SignatureFieldParameters();
            fieldParameters.setOriginX(Float.parseFloat(signatureParameters.getX()));
            fieldParameters.setOriginY(Float.parseFloat(signatureParameters.getY()));
            fieldParameters.setWidth(Float.parseFloat(signatureParameters.getWidth()));
            fieldParameters.setHeight(Float.parseFloat(signatureParameters.getHeight()));
            fieldParameters.setPage(signatureParameters.getPage());

            SignatureImageTextParameters textParameters = new SignatureImageTextParameters();
//            DSSFont font = new DSSFont("verdana",Font.PLAIN, signatureParameters.getTextSize());
//            textParameters.setFont(font);
            textParameters.setText(model.getSignatureText());
            textParameters.setTextColor(Color.BLACK);
            textParameters.setBackgroundColor(Color.WHITE);
            textParameters.setSignerTextPosition(SignerTextPosition.LEFT);
            textParameters.setSignerTextHorizontalAlignment(SignerTextHorizontalAlignment.RIGHT);
            textParameters.setSignerTextVerticalAlignment(SignerTextVerticalAlignment.TOP);


            imageParameters.setTextParameters(textParameters);
            imageParameters.setFieldParameters(fieldParameters);
            parameters.setImageParameters(imageParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSigned;
    }
}
