import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

public class WebBar extends LevelActor
{
    private int barWidth = 100; // the width of the color portion of the bar
    private int barHeight = 10; // the height of the color portion of the bar
    private Color backgroundColor = new Color(0,0,0,0); // the background color of the entire object
    private Color textColor = Color.WHITE; // the color of all text and the frame of the bar itself
    private Color barColor = Color.WHITE; // the color of the bar while in the safe range
    private Color previewColor = Color.LIGHT_GRAY; // the color of the bar while in the danger range
    private Color flashColor = Color.RED; // the color of the bar while in the danger range
    private float fontSize = 18.0f; // the size of the text
    private int value = 0; // the current value of the bar
    private int previewDelta = -10; // the current delta to preview
    private int maximumValue = 0; // the maximum value of the bar
    private int minimumValue = 0; // the minimum value of the bar
    private String referenceText = ""; // the title string (who or what the meter/bar is for)
    private String unitOfMeasure = ""; // the unit of measure of the bar (any quantitative standard of measurement) 
    private boolean showTextualUnits = true; // determines whether or not the textual quantity of the bar is to show
    
    private int flashFor = 0;
    
    public WebBar(int initValue, int maxValue)
    {
        referenceText = "Web";
        unitOfMeasure = "";
        maximumValue = maxValue;
        add(initValue);
    }
    
    private void newImage()
    {
        int barValue = (int) (barWidth * (value - minimumValue) / (maximumValue - minimumValue));
        int barPreviewValue = (int) (barWidth * ((value-previewDelta) - minimumValue) / (maximumValue - minimumValue));
        
        GreenfootImage leftImg = new GreenfootImage(referenceText + " ", (int) fontSize, textColor, backgroundColor);
        GreenfootImage rightImg = (showTextualUnits) ? new GreenfootImage(" " + value + " " + unitOfMeasure, (int) fontSize, textColor, backgroundColor) : new GreenfootImage(1, 1);
        int maxX = (leftImg.getWidth() > rightImg.getWidth()) ? leftImg.getWidth() : rightImg.getWidth();
        GreenfootImage barImg = new GreenfootImage(barWidth + 4, barHeight + 4);
        barImg.setColor(backgroundColor);
        barImg.fill();
        barImg.setColor(flashFor>0 ? flashColor : textColor);
        barImg.drawRect(0, 0, barImg.getWidth() - 1, barImg.getHeight() - 1);
        if (value > minimumValue)
        {
        	barImg.setColor(getBarColor());
            barImg.fillRect(2, 2, barPreviewValue, barHeight);
        }
        if (previewDelta != 0)
        {
            barImg.setColor(previewDelta >= value ? flashColor : previewColor);
            barImg.fillRect(2 + barPreviewValue, 2, (barValue-barPreviewValue), barHeight);
        }
        int sumX = 2 * maxX + barImg.getWidth();
        int maxY = 0;
        if (leftImg.getHeight() > maxY) maxY = leftImg.getHeight();
        if (barImg.getHeight() > maxY) maxY = barImg.getHeight();
        if (rightImg.getHeight() > maxY) maxY = rightImg.getHeight();
        GreenfootImage image = new GreenfootImage(sumX, maxY);
        image.setColor(backgroundColor);
        image.fill();
        image.drawImage(leftImg, maxX - leftImg.getWidth(), (image.getHeight() - leftImg.getHeight()) / 2);
        image.drawImage(barImg, maxX, (image.getHeight() - barImg.getHeight()) / 2);
        image.drawImage(rightImg, maxX + barImg.getWidth(), (image.getHeight() - rightImg.getHeight()) / 2);
        setImage(image);
    }
    
    @Override
    public void act() {
    	if(flashFor>0){
    		flashFor--;
    		if(flashFor==0){
    			newImage();
    		}
    	}
    }
    
    public void add(int amount)
    {
        value += amount;
        checkValue();
        newImage();
    }
    
    public void subtract(int amount)
    {
        value -= amount;
        checkValue();
        newImage();
    }
    
    private void checkValue()
    {
        if (value < minimumValue) value = minimumValue;
        if (value > maximumValue) value = maximumValue;
    }
    
    private void checkPreview() {
		if(previewDelta < 0) previewDelta = 0;
		if(previewDelta > value) previewDelta = value;
	}
    
    public int getValue() { return value; }
    
    public int getBarWidth() { return barWidth; }
    
    public int getBarHeight() { return barHeight; }
    
    public Color getBackgroundColor() { return backgroundColor; }
    
    public Color getTextColor() { return textColor; }
    
    public Color getSafeColor() { return barColor; }
    
    public float getFontSize() { return fontSize; }
    
    public int getMaximumValue() { return maximumValue; }
    
    public int getMinimumValue() { return minimumValue; }
    
    public String getReferenceText() { return referenceText; }
    
    public String getUnitOfMeasure() { return unitOfMeasure; }
    
    public boolean getShowTextualUnits() { return showTextualUnits; }
    
    public void setValue(int val) { value = val; checkValue(); newImage(); }
    
    public void setBarWidth(int width) { if (width > 9) { barWidth = width; newImage(); } }
    
    public void setBarHeight(int height) { if (height > 1) { barHeight = height; newImage(); } }
    
    public void setBackgroundColor(Color color) { backgroundColor = color; newImage(); }
    
    public void setTextColor(Color color) { textColor = color; newImage(); }
    
    public void setSafeColor(Color color) { barColor = color; newImage(); }
    
    public void setFontSize(float size) { if (size > 7) { fontSize = size; newImage(); } }
    
    public void setMaximumValue(int maxVal) { if (maxVal > minimumValue) { maximumValue = maxVal; newImage(); } }
    
    public void setMinimumValue(int minVal) { if (minVal < maximumValue) { minimumValue = minVal; newImage(); } }
    
    public void setReferenceText(String refText) { referenceText = refText; newImage(); }
    
    public void setUnitOfMeasure(String uom) { unitOfMeasure = uom; newImage(); }
    
    public void setShowTextualUnits(boolean show) { showTextualUnits = show; newImage(); }

	public Color getBarColor() { return flashFor>0 ? flashColor : barColor;}

	public void setBarColor(Color barColor) { this.barColor = barColor;}

	public Color getPreviewColor() { return previewColor;}

	public void setPreviewColor(Color previewColor) { this.previewColor = previewColor;}

	public int getPreviewDelta() { return previewDelta;}

	public void setPreviewDelta(int previewDelta) { this.previewDelta = previewDelta; checkPreview(); newImage(); }

	public Color getFlashColor() { return flashColor;	}

	public void setFlashColor(Color flashColor) { this.flashColor = flashColor;	}

	public int getFlashFor() { return flashFor;	}

	public void flash(int frames) { this.flashFor = frames; newImage(); }
}