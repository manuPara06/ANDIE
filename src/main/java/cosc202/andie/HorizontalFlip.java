/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author shika747
 */
package cosc202.andie;

import cosc202.andie.ImageOperation;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class HorizontalFlip implements ImageOperation, java.io.Serializable {

    @Override
    public BufferedImage apply(BufferedImage input) {
        AffineTransform transform = new AffineTransform();

        //flip horizontally 
        transform.scale(-1, 1);

        //move image back 
        transform.translate(-input.getWidth(), 0);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage output = op.filter(input, null);

        
        return output;
    }
}
