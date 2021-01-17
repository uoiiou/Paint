package tcp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;
import tcp.client.ClientThread;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Canvas canvas;

    Runnable clientthread = new Runnable() {
        @Override
        public void run() {
            GraphicsContext graphicsContext;
            int i = 0;
            double x0 = 0, y0 = 0;
            
            while (true) {
                List<Control.Point> list = Control.getInstance().getPoints();
                if (Control.getExit())
                    break;
                
                if (list.size() != 0)
                {
                    for (Control.Point point:list)
                    {
                        double x = point.x - point.bsize / 2;
                        double y = point.y - point.bsize / 2;

                        if (!point.flag)
                        {
                            graphicsContext = canvas.getGraphicsContext2D();
                            graphicsContext.setFill(Color.web(list.get(0).color));
                            graphicsContext.fillRoundRect(
                                    point.x,
                                    point.y,
                                    point.bsize,
                                    point.bsize,
                                    point.bsize,
                                    point.bsize);
                        }
                        else
                        {
                            graphicsContext = canvas.getGraphicsContext2D();
                            graphicsContext.setFill(Color.web(list.get(0).color));
                            graphicsContext.fillRoundRect(
                                    point.x,
                                    point.y,
                                    point.bsize,
                                    point.bsize,
                                    point.bsize,
                                    point.bsize);

                            graphicsContext.setStroke(Color.web(point.color));
                            graphicsContext.setLineWidth(point.bsize * 0.88);
                            graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
                            graphicsContext.strokeLine(
                                    x0 + point.bsize, y0 + point.bsize,
                                    x + point.bsize, y + point.bsize);
                        }

                        x0 = x;
                        y0 = y;
                        System.out.println("Client - " + point.x + " " + point.y + " " +
                                point.color + " " + point.bsize + " count = " + i++);
                    }
                }
                else
                {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex)
                    {
                        System.out.println("(ClientController) - " + ex);
                    }
                }
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new ClientThread().start();
        new Thread(clientthread).start();
    }

    @FXML
    void clear() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}