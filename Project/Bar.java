import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

public class Bar extends LevelActor
{
    private int barWidth = 100; // the width of the color portion of the bar
    private int barHeight = 10; // the height of the color portion of the bar
    private int breakPercent = 20; // the percentage amount that changes the color of the bar
    private int breakValue = 20; // in tandem with breakPercent
    private boolean usingBreakValue = false;
    private boolean breakLow = true; // when true, with low-percent values bar is dangerColor, else safeColor; reversed when false
    private Color backgroundColor = new Color(0, 0, 0, 0); // the background color of the entire object
    private Color textColor = Color.BLACK; // the color of all text and the frame of the bar itself
    private Color safeColor = Color.GREEN; // the color of the bar while in the safe range
    private Color dangerColor = Color.RED; // the color of the bar while in the danger range
//  The color of the bar AT the breakpoint will be the average color between the safe color and the danger color
    private float fontSize = 18.0f; // the size of the text
    private int value = 0; // the current value of the bar
    private int maximumValue = 0; // the maximum value of the bar
    private int minimumValue = 0; // the minimum value of the bar
    private String referenceText = ""; // the title string (who or what the meter/bar is for)
    private String unitOfMeasure = ""; // the unit of measure of the bar (any quantitative standard of measurement) 
    private boolean showTextualUnits = true; // determines whether or not the textual quantity of the bar is to show
    
    public Bar(String refText, String unitType, int initValue, int maxValue)
    {
        referenceText = refText;
        unitOfMeasure = unitType;
        maximumValue = maxValue;
        add(initValue);
    }
    
    private void newImage()
    {
//         int barValue = (int) (barWidth * value / maximumValue);
        int barValue = (int) (barWidth * (value - minimumValue) / (maximumValue - minimumValue));
        GreenfootImage leftImg = new GreenfootImage(referenceText + " ", (int) fontSize, textColor, backgroundColor);
        GreenfootImage rightImg = (showTextualUnits) ? new GreenfootImage(" " + value + " " + unitOfMeasure, (int) fontSize, textColor, backgroundColor) : new GreenfootImage(1, 1);
        int maxX = (leftImg.getWidth() > rightImg.getWidth()) ? leftImg.getWidth() : rightImg.getWidth();
        GreenfootImage barImg = new GreenfootImage(barWidth + 4, barHeight + 4);
        barImg.setColor(backgroundColor);
        barImg.fill();
        barImg.setColor(textColor);
        barImg.drawRect(0, 0, barImg.getWidth() - 1, barImg.getHeight() - 1);
        if (value > minimumValue)
        {
            if (breakLow)
            {
                if (value > (usingBreakValue ? breakValue : (int) (breakPercent * (maximumValue - minimumValue) / 100 + minimumValue))) barImg.setColor(safeColor);
                else barImg.setColor(dangerColor);
            }
            else
            {
                if (value < (usingBreakValue ? breakValue : (int) (breakPercent * (maximumValue - minimumValue) / 100 + minimumValue))) barImg.setColor(safeColor);
                else barImg.setColor(dangerColor);
            }
            if (value == (usingBreakValue ? breakValue : (int) (breakPercent * (maximumValue - minimumValue) / 100 + minimumValue)))
            {
                int r = (int) ((safeColor.getRed() + dangerColor.getRed()) / 2);
                int g = (int) ((safeColor.getGreen() + dangerColor.getGreen()) / 2);
                int b = (int) ((safeColor.getBlue() + dangerColor.getBlue()) / 2);
                barImg.setColor(new Color(r, g, b));
            }
            barImg.fillRect(2, 2, barValue, barHeight);
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
    
    public int getValue() { return value; }
    
    public int getBarWidth() { return barWidth; }
    
    public int getBarHeight() { return barHeight; }
    
    public int getBreakPercent() { return breakPercent; } // use boolean getUsingBreakValue() method before calling (if false)
    
    public int getBreakValue() { return breakValue; } // use boolean getUsingBreakValue() method before calling (if true)
    
    public boolean getBreakLow() { return breakLow; }
    
    public Color getBackgroundColor() { return backgroundColor; }
    
    public Color getTextColor() { return textColor; }
    
    public Color getSafeColor() { return safeColor; }
    
    public Color getDangerColor() { return dangerColor; }
    
    public float getFontSize() { return fontSize; }
    
    public int getMaximumValue() { return maximumValue; }
    
    public int getMinimumValue() { return minimumValue; }
    
    public String getReferenceText() { return referenceText; }
    
    public String getUnitOfMeasure() { return unitOfMeasure; }
    
    public boolean getShowTextualUnits() { return showTextualUnits; }
    
    public void setValue(int val) { value = val; checkValue(); newImage(); }
    
    public void setBarWidth(int width) { if (width > 9) { barWidth = width; newImage(); } }
    
    public void setBarHeight(int height) { if (height > 1) { barHeight = height; newImage(); } }
    
    public void setBreakPercent(int percent) { if (percent >= 0 && percent <= 100) { breakPercent = percent; usingBreakValue = false; newImage(); } }
    
    public void setBreakValue(int brkVal) { if (brkVal >= minimumValue && brkVal <= maximumValue) {breakValue = brkVal; usingBreakValue = true; newImage(); } }
    
    public void setBreakLow(boolean lowBreak) { breakLow = lowBreak; newImage(); }
    
    public void setBackgroundColor(Color color) { backgroundColor = color; newImage(); }
    
    public void setTextColor(Color color) { textColor = color; newImage(); }
    
    public void setSafeColor(Color color) { safeColor = color; newImage(); }
    
    public void setDangerColor(Color color) { dangerColor = color; newImage(); }
    
    public void setFontSize(float size) { if (size > 7) { fontSize = size; newImage(); } }
    
    public void setMaximumValue(int maxVal) { if (maxVal > minimumValue) { maximumValue = maxVal; newImage(); } }
    
    public void setMinimumValue(int minVal) { if (minVal < maximumValue) { minimumValue = minVal; newImage(); } }
    
    public void setReferenceText(String refText) { referenceText = refText; newImage(); }
    
    public void setUnitOfMeasure(String uom) { unitOfMeasure = uom; newImage(); }
    
    public void setShowTextualUnits(boolean show) { showTextualUnits = show; newImage(); }
}