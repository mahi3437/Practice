package base;

import com.google.common.collect.ImmutableList;
import driver.AppDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * Utility class containing methods for performing various gestures and actions using Appium.
 */
public class Util {
//    static double SCROLL_RATIO = 0.5;
//    static Duration SCROLL_DUR = Duration.ofMillis(500);
    /**
     * Swipe on the specified WebElement in the given direction.
     *
     * @param element   The WebElement to swipe on.
     * @param direction The direction of the swipe ("left", "right", "up", or "down").
     */
    public static void Swipe(WebElement element,String direction){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: swipe",
                        Map.of( "elementId", ((RemoteWebElement)element).getId(),
                                "percentage", 50,
                                "direction", direction));

    }
    /**
     * Swipe left on the specified WebElement.
     *
     * @param element The WebElement to swipe left on.
     */
    public static void SwipeLeft(WebElement element){
//        RemoteWebElement carousel = (RemoteWebElement) wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("Carousel")));

        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: swipe",
                        Map.of( "elementId", ((RemoteWebElement)element).getId(),
                                "percentage", 50,
                                "direction", "left"));
    }
    /**
     * Swipe right on the specified WebElement.
     *
     * @param element The WebElement to swipe right on.
     */
    public static void SwipeRight(WebElement element){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: swipe",
                        Map.of( "elementId", ((RemoteWebElement)element).getId(),
                                "percentage", 50,
                                "direction", "right"));
    }
    /**
     * Swipe up on the specified WebElement.
     *
     * @param element The WebElement to swipe up on.
     */
    public static void SwipeUp(WebElement element){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
         .executeScript("gesture: swipe",
                        Map.of( "elementId", ((RemoteWebElement)element).getId(),
                                "percentage", 50,
                                "direction", "up"));

    }
    /**
     * Swipe Down on the specified WebElement.
     *
     * @param element The WebElement to swipe Down on.
     */
    public static void SwipeDown(WebElement element){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: swipe",
                        Map.of( "elementId", ((RemoteWebElement)element).getId(),
                                "percentage", 50,
                                "direction", "down"));

    }
    /**
     * Scroll the specified WebElement into view.
     *
     * @param element The WebElement to scroll into view.
     */
    public static void scrollElementIntoView(WebElement element) {
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: scrollElementIntoView",
                        Map.of( "scrollableView", ((RemoteWebElement) element).getId(),
                                "strategy", "accessibility id",
                                "selector", "WebdriverIO logo",
                                "percentage", 50,
                                "direction", "up",
                                "maxCount", 3));
    }
    /**
     * Perform a drag-and-drop operation between two WebElements.
     *
     * @param source      The source WebElement to drag.
     * @param destination The destination WebElement to drop onto.
     */
    public static void DragAndDrop(WebElement source,WebElement destination){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: dragAndDrop",
                        Map.of( "sourceId", ((RemoteWebElement) source).getId(),
                                "destinationId", ((RemoteWebElement) destination).getId()));


    }
    /**
     * Perform a double tap on the specified WebElement.
     *
     * @param element The WebElement to double tap.
     */
    public static void DoubleTap(WebElement element){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: doubleTap",
                        Map.of("elementId", ((RemoteWebElement) element).getId()));

    }
    /**
     * Perform a long press on the specified WebElement with the given pressure and duration.
     *
     * @param element The WebElement to long press.
     */
    public static void LongPress(WebElement element){
        ((JavascriptExecutor) AppDriver.getCurrentDriver())
                .executeScript("gesture: longPress",
                        Map.of( "elementId",((RemoteWebElement) element).getId(),
                                "pressure", 0.5,
                                "duration", 800));

    }
    /**
     * Capture a screenshot and save it with the specified image name.
     *
     * @param imagename The name to be given to the captured screenshot.
     * @throws IOException If there is an issue while copying the screenshot file.
     * @see TakesScreenshot
     * @see FileUtils
     */
    public static void getScreenshot(String imagename) throws  IOException {
        TakesScreenshot ts = (TakesScreenshot) AppDriver.getCurrentDriver();
        File f = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "./screenshot/"+imagename+".jpg";
        FileUtils.copyFile(f, new File(filePath));
    }
    /**
     * Captures a screenshot of a specific WebElement within an AppiumDriver session.
     *
     * @param driver The AppiumDriver instance representing the active session.
     * @param element    The WebElement for which the screenshot needs to be captured.
     * @return A String representing the file path where the screenshot is saved.
     *         If an error occurs during the process, returns null.
     *
     * <p>This method takes an AppiumDriver instance and a WebElement as input,
     * captures a screenshot of the specified WebElement, and saves it as a JPG file.
     * The screenshot is cropped to include only the specified WebElement.</p>
     *
     * <p>The file path of the saved screenshot is returned as a String.
     * If an IOException occurs during the screenshot capture or file saving process,
     * the method prints the stack trace and returns null.</p>
     *
     * <p>The saved screenshot file is named using a UUID to ensure uniqueness.</p>
     *
     * @see TakesScreenshot
     * @see OutputType
     * @see BufferedImage
     * @see ImageIO
     * @see FileUtils
     * @see File
     * @see Point
     */
    public static String elementScreenshot(AppiumDriver driver, WebElement element)
    {

        File screenshotLocation = null;
        try{
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            BufferedImage fullImg = ImageIO.read(scrFile);
//Get the location of element on the page
            Point point = element.getLocation();
//Get width and height of the element
            int eleWidth = element.getSize().getWidth();
            int eleHeight = element.getSize().getHeight();
//Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth,
                    eleHeight);
            ImageIO.write(eleScreenshot, "jpg", scrFile);

            String path = "screenshots/" + UUID.randomUUID() + "" + ".jpg";

            screenshotLocation = new File(System.getProperty("user.dir") + "/" + path);
            FileUtils.copyFile(scrFile, screenshotLocation);

            System.out.println(screenshotLocation.toString());

        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return screenshotLocation.toString();

    }
//    public static void scrollNclick(By byEl) {
//        String prevPageSource = "";
//        boolean flag = false;
//
//        while (!isEndOfPage(prevPageSource)) {
//            prevPageSource = AppDriver.getCurrentDriver().getPageSource();
//
//            try {
//                AppDriver.getCurrentDriver().findElement(byEl).click();
//            } catch (org.openqa.selenium.NoSuchElementException e) {
//                scroll(ScrollDirection.DOWN, Util.SCROLL_RATIO);
//            }
//        }
//
//    }
//    public static boolean isEndOfPage(String pageSource) {
//        return pageSource.equals(AppDriver.getCurrentDriver().getPageSource());
//    }
//    public enum ScrollDirection {
//        UP, DOWN, LEFT, RIGHT
//    }
//
//    public static void scroll(ScrollDirection dir, double scrollRatio) {
//
//        if (scrollRatio < 0 || scrollRatio > 1) {
//            throw new Error("Scroll distance must be between 0 and 1");
//        }
//        Dimension size = AppDriver.getCurrentDriver().manage().window().getSize();
//        System.out.println(size);
//        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
//        int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
//        int top = midPoint.y - (int) (midPoint.y * scrollRatio);
//        //Point Start = new Point(midPoint.x, bottom );
//        //Point End = new Point(midPoint.x, top );
//        int left = midPoint.x - (int) (midPoint.x * scrollRatio);
//        int right = midPoint.x + (int) (midPoint.x * scrollRatio);
//
//        if (dir == ScrollDirection.UP) {
//            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
//        } else if (dir == ScrollDirection.DOWN) {
//            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
//        } else if (dir == ScrollDirection.LEFT) {
//            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
//        } else {
//            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
//        }
//    }
//    protected static void swipe(Point start, Point end, Duration duration) {
//
//        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
//        Sequence swipe = new Sequence(input, 0);
//        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
//        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        /*if (isAndroid) {
//            duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
//        } else {
//            swipe.addAction(new Pause(input, duration));
//            duration = Duration.ZERO;
//        }*/
//        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
//        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//        ((AppiumDriver) AppDriver.getCurrentDriver()).perform(ImmutableList.of(swipe));
//    }


}
