package tcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Control {
    static class Point{
        public double x, y, bsize;
        public String color;
        public boolean flag;
    }
    public List<Point> list = Collections.synchronizedList(new ArrayList<>());
    private static boolean exit = false;

    private static Control instance;

    private Control(){}

    public static Control getInstance(){
        if (instance == null)
            instance = new Control();
        return instance;
    }

    public void setPoint(double x0, double y0, String color0, double bsize0, boolean flag0){
        Point point = new Point();
        point.x = x0;
        point.y = y0;
        point.color = color0;
        point.bsize = bsize0;
        point.flag = flag0;

        list.add(point);
    }

    public List<Point> getPoints(){
        List<Point> listcopy = Collections.synchronizedList(new ArrayList<>());
        listcopy.addAll(list);
        list = Collections.synchronizedList(new ArrayList<>());
        return listcopy;
    }

    public static void setExit(boolean flag){
        exit = flag;
    }

    public static boolean getExit(){
        return exit;
    }
}
