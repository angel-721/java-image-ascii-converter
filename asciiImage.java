/**
 * class that takes image files and holds data
 * that represents the image as an Ascii string
 *
 * @author Angel Velasquez
 *
 *
 * */
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class asciiImage{
	/**
	 * the image object
	 * */
	protected BufferedImage mImage;	

	/**
	 * Matrix of pixels from the image
	 * */
	protected int[][] mImageMatrix;

	/**
	 * Array of chars that represent the image in Ascii
	 * */
	protected char[] mAsciiChars;

	/**
	 * Height of the image and pixel matrix
	 * */
	protected int mHeight;

	/**
	 * Width of the image and pixel matrix
	 * */
	protected int mWidth;
	
	/**
	 * asciiImage default constructor
	*/
	public asciiImage(){
		mImage = null;
		mAsciiChars = null;
		mHeight = 0;
		mWidth = 0;
	}


	/**
	 * Loads an image into the object
	 * @param image the input image file and set image fields
	 *
	 * */
	public void setImage(File image){
		// Read the image
		System.out.println("reading image...");
		if(image == null){
			return;
		}
		try{
			this.mImage = ImageIO.read(image);
		}
		catch(Exception e){
			System.out.println("Error reading image.");
			System.out.println("Aborting program.");
			System.exit(1);
		}
		System.out.println("Image was read with no errors.");
		this.setWidth();
		this.setHeight();
		this.setImageHelper();
	}

	/**
	 * Setter for width field
	 */
	private void setWidth(){
		if(this.mImage == null){
			return;
		}
		this.mWidth = this.mImage.getWidth();
	}

	/**
	 * Setter for height field
	 */
	private void setHeight(){
		if(this.mImage == null){
			return;
		}
		this.mHeight = this.mImage.getHeight();
	}
	
	/**
	 * Helper for setImage. Sets the imageMatrix
	 */
	private void setImageHelper(){
		if(this.mImage == null){
			return;
		}
		// if the image is read then set the matrix of pixels
		int row, col;
		this.mImageMatrix = new int[this.mHeight][this.mWidth];
		// loop through the height and width of the matrix
		for(row = 0; row < this.mHeight; row++){
			for(col = 0; col < this.mWidth; col++){
				this.mImageMatrix[row][col] = this.mImage.getRGB(col, row);
			}
		}
	}

	/**
	 * sets the AsciiImage matrix from the pixel matrix
	 * */
	public void setAsciiImage(){
		int row, col;
		double pixelBrightness;
		int pixel = 0;
		if(this.mImageMatrix == null){
			return;
		}
		this.mAsciiChars = new char[this.mWidth*this.mHeight];
		for(row=0; row < this.mHeight; row++){
			for (col=0; col < this.mWidth; col++){
				pixelBrightness = this.calculatePixelBrightness(row, col);
				this.mAsciiChars[pixel] = calculateAsciiPixel(pixelBrightness);
				pixel++;
			}
		}
	}

	/**
	 * @param row row index at image
	 * @param col column index at image
	 * @return brightness integer of a pixel
	 * 
	 * */
	private double calculatePixelBrightness(int row, int col){
		if(this.mImageMatrix == null){
			return -1.0;
		}
		int r, g, b;
		r = (this.mImageMatrix[row][col] >> 16) & (255);
		g = (this.mImageMatrix[row][col] >> 8) & (255);
		b = this.mImageMatrix[row][col] & 255;
		double sum = r + g + b;
		return sum / 765.0;
	}

	/**
	 * Turns pixelBrightness into an Ascii character
	 * */
	private char calculateAsciiPixel(double pixelBrightness){
		char[] asciiChars = {' ', '.', '°', '*', 'o', 'O', '#', '@'};
		return asciiChars[(int)(pixelBrightness*8)]; }

	/**
	 * print the ascii image
	 * */
	public void printAsciiImage(){
		int row, col;
		int pixel = 0;
		if(this.mAsciiChars == null){
			return;
		}
		for(row=0; row < this.mHeight; row++){
			for (col=0; col < this.mWidth; col++){
				System.out.print(this.mAsciiChars[pixel]);
				pixel++;
			}
			System.out.println();
		}
	}

	/**
	 * Use helper methods to create 
	 * an ascii image and print it
	 * */
	public void createAsciiImage(File image){
		this.setImage(image);
		this.setAsciiImage();
		this.printAsciiImage();

	}
}
