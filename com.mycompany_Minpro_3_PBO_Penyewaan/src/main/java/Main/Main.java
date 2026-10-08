package Main;

/**
 *
 * @author mismu
 */
import Controller.SewaController;
import View.SewaView;

public class Main {
    public static void main(String[] args) {
        SewaView view = new SewaView();
        SewaController controller = new SewaController(view);
        controller.mulai();
    }
}
