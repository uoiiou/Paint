package tcp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.shape.StrokeLineJoin;
import tcp.server.ServerThread;

import java.net.URL;
import java.util.ResourceBundle;

public class PaintController implements Initializable {
    private boolean flagfirstpress;
    private double x0, y0;
    private double bsize;

    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorpickerb;
    @FXML
    private Slider slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new ServerThread().start();

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        slider.setOnMouseMoved(e -> bsize = (int) slider.getValue());

        canvas.setOnMousePressed(e -> {
            x0 = e.getX();
            y0 = e.getY();
            flagfirstpress = false;
        });

        canvas.setOnMouseDragged(e -> {
            double x = e.getX() - bsize / 2;
            double y = e.getY() - bsize / 2;

            graphicsContext.setFill(colorpickerb.getValue());
            graphicsContext.fillRoundRect(e.getX(), e.getY(), bsize, bsize, bsize, bsize);

            if (flagfirstpress)
            {
                graphicsContext.setStroke(colorpickerb.getValue());
                graphicsContext.setLineWidth(bsize * 0.88);
                graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
                graphicsContext.strokeLine(x0 + bsize, y0 + bsize, x + bsize, y + bsize);
            }

            sendpoints(x, y, String.valueOf(colorpickerb.getValue()), bsize);

            x0 = x;
            y0 = y;
            flagfirstpress = true;
        });
    }

    private void sendpoints(double x0, double y0, String color0, double bsize0){
        if (flagfirstpress)
            ServerThread.send(x0, y0, color0, bsize0, true);
        else
            ServerThread.send(x0, y0, color0, bsize0, false);
    }

    @FXML
    void clear() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}