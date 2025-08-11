package granicni_prelaz.javaprojekat2023.util;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.Terminal;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

    public class TerminalWatcher extends Thread {
        private static final Path directory = Paths.get(Constants.TERMINALS_DIRECTORY);

        @Override
        public void run() {

            List<Terminal> terminalList = new ArrayList<>(Simulation.customsTerminals);
            terminalList.addAll(Simulation.policeTerminals);

            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                directory.register(watcher, ENTRY_MODIFY);

                while (true) {

                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException ex) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();

                        if (fileName.toString().trim().startsWith("terminals") && fileName.toString().trim().endsWith(".txt") && kind.equals(ENTRY_MODIFY)) {
                            List<String> content = Files.readAllLines(directory.resolve(fileName));
                            if (content.size() == Constants.NUMBER_OF_TERMINALS) {
                                synchronized (Simulation.pathWithTerminals) {
                                    for (int i = 0; i < Constants.NUMBER_OF_TERMINALS; i++) {

                                        if ("1".equals(content.get(i))) {
                                            terminalList.get(i).setInFunction(true);
                                        } else {
                                            terminalList.get(i).setInFunction(false);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }

            } catch (IOException exception) {
                SimulationLogger.log(TerminalWatcher.class, Level.SEVERE, exception.getMessage(), exception);
            }
        }
    }


