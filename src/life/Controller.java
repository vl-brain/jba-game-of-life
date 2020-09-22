package life;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class Controller {
    private Model model;
    private GameOfLife view;
    private Thread periodicalTask;
    private int timeout = 1;

    public Controller(Model model, GameOfLife view) {
        this.model = model;
        this.view = view;
        this.periodicalTask = getPeriodicalTask();
        periodicalTask.start();
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (periodicalTask.getState() != Thread.State.TERMINATED) {
                    periodicalTask.interrupt();
                }
                super.windowClosing(e);
            }
        });
        view.addPauseToggleButtonListener((e -> {
            if (periodicalTask.getState() == Thread.State.TERMINATED) {
                periodicalTask = getPeriodicalTask();
                periodicalTask.start();
            } else {
                periodicalTask.interrupt();
                while (periodicalTask.getState() != Thread.State.TERMINATED) {
                }
            }
            view.togglePauseButton();
        }));
        view.addResetButtonListener((e -> {
            setModel(new Model(Universe.generate(model.getUniverse().getSize())));
            view.update(model);
        }));
    }

    private Thread getPeriodicalTask() {
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    break;
                }
                nextGeneration();
            }
        });
    }

    private void setModel(Model model) {
        this.model = model;
    }

    public void nextGeneration() {
        model = model.nextGeneration();
        view.update(model);
    }
}
