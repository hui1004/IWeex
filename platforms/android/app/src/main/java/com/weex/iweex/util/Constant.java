package com.weex.iweex.util;

/**
 * Created by aoxiao on 2017/1/4.
 */

public class Constant {

  public static interface Value {
    int SCROLLGESTURE = 0x1;
    int ZOOMGESTURE = 0x1 << 1;
    int TILTGESTURE = 0x1 << 2;
    int ROTATEGESTURE = 0x1 << 3;
  }
  public static interface MapProp {
     String POINT="point";
     String TITLE="title";
     String CONTENT="content";
     String IMGSRC="imageSrc";
     String DRAGGABLE="draggable";
     String FLAT="isFlat";
    String ANIMATION="isAnimation";
    String CIRCLE_LAT="lat";
    String CIRCLE_LNG="lng";
    String CIRCLE_RADIUS="radius";
    String CIRCLE_STROKE_WIDTH="strokeWidth";
    String CIRCLE_STROKE_COLOR="strokeColor";
    String CIRCLE_FILL_COLOR="fillColor";
  }
  public interface Name {

    // mapview
    String SCALECONTROL = "scale";
    String ZOOM_ENABLE = "zoomEnable";
    String ZOOM = "zoom";
    String COMPASS = "compass";
    String GEOLOCATION = "geolocation";
    String GESTURE = "gesture";
    String INDOORSWITCH = "indoorswitch";
    String CENTER = "center";
    String KEYS = "sdkKey";

    // marker
    String MARKER = "marker";
    String POSITION = "position";
    String ICON = "icon";
    String TITLE = "title";
    String HIDE_CALL_OUT = "hideCallout";

    // polyline
    String PATH = "path";
    String STROKE_COLOR = "strokeColor";
    String STROKE_WIDTH = "strokeWidth";
    String STROKE_OPACITY = "strokeOpacity";
    String STROKE_STYLE = "strokeStyle";

    // circle
    String RADIUS = "radius";
    String FILL_COLOR = "fillColor";

    // offset
    String OFFSET = "offset";
    String OPEN = "open";
  }



  public static interface EVENT {
    String ZOOM_CHANGE = "zoomchange";
    String DRAG_CHANGE = "dragend";
  }
}
