# Border Crossing Simulation (JavaFX)

This repository contains a JavaFX application that simulates traffic through a border crossing. The simulation models a queue of vehicles (cars, buses, trucks) moving along a predefined path toward **Police** and **Customs** terminals. Each vehicle carries a driver and passengers with identification documents (and possibly suitcases/customs documents). Terminals process vehicles under simple rules, incidents are recorded, and a GUI shows the state of the system in real time.

> Educational project: focuses on JavaFX UI, threads/concurrency, basic file I/O, and JSON parsing. It is not a production traffic system.

## Demo
<div align="center">
  <img src="screenshots/demo.gif" alt="Project Demo" width="600">
</div>


---

## What it does

- Builds a **path** across a grid (loaded from `res/json/path.json`) and places an initial column of vehicles at the start segment.
- Spawns a **queue of vehicles** (`Car`, `Bus`, `Truck`) with random occupants and attributes.
- Moves vehicles along the path toward **PoliceTerminal** and **CustomsTerminal** instances, where they are processed.
- Validates simple conditions (e.g., ID validity, customs documentation) and records **incidents** and **punished persons** when rules are broken.
- Logs simulation activity to `res/logs/` and writes outputs (lists/records) to `Records/` (per type of incident).
- Provides a **JavaFX GUI** to watch the path, the live queue/column, terminal statuses, and to inspect vehicles/records.

---

## Key features (from the code)

- **JavaFX UI** with FXML (`hello-view.fxml`, `columnOfVehiclesView.fxml`, `vehicleView.fxml`, `customsRecordView.fxml`, `policeRecordView.fxml`).
- **Domain model** under `granicni_prelaz.javaprojekat2023`:
  - Vehicles: `Vehicle` (base), `Car`, `Bus`, `Truck` with passengers and a driver.
  - Persons: `Person`, `Driver`, `Passenger`, each with an `IdentificationDocument` and optional `Suitcase`/`CustomsDocumentation`.
  - Map & Path: `Field`, `PathOnMap`; JSON loader `PathJsonParser` builds the path from `res/json/path.json`.
  - Terminals: abstract `Terminal`, concrete `PoliceTerminal` and `CustomsTerminal` (+ specialized variants for trucks). Each terminal processes the current vehicle and updates the UI/logs.
  - Incidents & Records: `IncidentUtil`, `ListOfPunishedPersons`; writes to `Records/PoliceRecords` and `Records/CustomsRecords`.
  - Utilities: `SimulationLogger` (Java util logging to `res/logs`), `TerminalWatcher` (watches the `Terminals/` directory for changes), `Utils` (GUI helpers, synchronization helpers), `Constants` (central configuration).

- **Simulation loop** (`simulation/Simulation`):
  - Initializes the path and places the first set of vehicles starting from `Constants.START_INDEX`.
  - Advances vehicles along the path, synchronizes with the UI (Platform threads), and blocks/resumes when paused.
  - Interacts with terminals (police, customs) and pushes processed vehicles out of the system (or to records if punished).

---

## User interface

- **Main window** (`hello-view.fxml`, controller: `SimulationController`)
  - Path/board view and **terminal panels** with status text (busy/free, in function).
  - Buttons to start/pause the simulation, open **column view**, open **police/customs records** windows.
  - Runtime labels for **elapsed time** and other indicators.
- **Column of vehicles** (`columnOfVehiclesView.fxml`, controller: `ColumnOfVehiclesController`)
  - A grid showing the current *column* near the start of the path; vehicles are colored by type.
- **Vehicle details** (`vehicleView.fxml`, controller: `VehicleController`)
  - Shows the selected vehicle’s driver and passenger list (via `Vehicle.toString()`).
- **Records windows** (`policeRecordView.fxml`, `customsRecordView.fxml`)
  - List the available files under `Records/PoliceRecords/` and `Records/CustomsRecords/` and let you open/read their contents.

---

## Files and output

- **Path JSON**: `res/json/path.json` – a JSON array of `{row, column}` entries defining the path across the grid.
- **Logs**: `res/logs/` – log files produced by `SimulationLogger` (per-class log files with timestamps).
- **Records**: `Records/`
  - `PoliceRecords/` – serialized or textual records for punished persons produced by police checks.
  - `CustomsRecords/` – records/files produced by customs checks.
- **Terminals directory**: `Terminals/` – watched by `TerminalWatcher`; file changes can be used to simulate “terminal in/out of function” toggles or other events.

> The project creates these directories if they do not exist (see `Utils.createFolderIfNotExists` and constants for paths).

---

## Important classes (quick map)

- `HelloApplication` – JavaFX entry point; loads the main FXML and exposes a reference to `SimulationController`.
- `controllers.SimulationController` – central controller for the main window; wires buttons, initializes the path/terminals, launches the `Simulation` thread, opens auxiliary views.
- `simulation.Simulation` – simulation thread: positions the initial column, moves vehicles, interacts with terminals, writes logs/records, handles pause/resume (synchronization on `PathOnMap`).
- `vehicles.Vehicle` (+ `Car`, `Bus`, `Truck`) – vehicle state, occupants, position along the path; `toString()` prints a human-readable summary used in the vehicle details window.
- `terminals.Terminal` (abstract), `PoliceTerminal`, `CustomsTerminal`, and truck-specific terminal classes – process a vehicle’s occupants/documents and set a message via `printInfo`, which the UI displays.
- `json.PathJsonParser` – reads `res/json/path.json` into a `PathOnMap` instance.
- `incident.IncidentUtil`, `incident.ListOfPunishedPersons` – build and persist incident outputs (and list of punished persons) to `Records/*`.
- `util.SimulationLogger` – file logger; `util.TerminalWatcher` – NIO watcher thread for `Terminals/` directory.
- `constants.Constants` – configuration: colors, counts (e.g., `NUMBER_OF_VEHICLES`, per-type passenger limits), file paths, format strings.

---

## Build and run

This is a **Maven** project using JavaFX 17.0.2 and modules (`module-info.java`).

### Requirements
- **JDK 17** (recommended) – matches the JavaFX version in `pom.xml`.
- **Maven 3.8+** – or use the included Maven Wrapper (`mvnw`, `mvnw.cmd`).

### Run (recommended via Maven Wrapper)
From the project root (the folder containing `pom.xml`):

```bash
# Windows
mvnw.cmd clean javafx:run

# macOS / Linux
./mvnw clean javafx:run
```

This will launch the JavaFX app. The main class is:
```
granicni_prelaz.javaprojekat2023.HelloApplication
```

### Build a jar / image
The `pom.xml` is configured with the JavaFX plugin to run the app. To produce artifacts:

```bash
# standard build (tests included if present)
mvnw.cmd clean package   # Windows
./mvnw clean package     # macOS/Linux
```

If you need a fat JAR or native image, you can add/configure plugins (e.g., Maven Shade, jlink) as needed.

---

## How to use the app

1. Run the application (see above). The main window appears.
2. Press **Start** to begin the simulation. Vehicles start advancing along the path.
3. Watch **terminal panels** for status/messages (busy, document checks, customs checks).
4. Click **Column** to open the column grid; click an individual vehicle (or use the main window’s list/controls) to open **Vehicle details**.
5. Use the **Police Records** or **Customs Records** buttons to open windows listing output files; select a file to display its contents.
6. Pause/Resume using the provided control (the simulation uses wait/notify on the path object).

If you get “file not found” or “could not load path” errors, ensure the working directory is the project root so relative paths like `res/json/path.json` resolve.

---

## Configuration knobs

Most knobs are in `constants/Constants`:

- Vehicle counts: `NUMBER_OF_VEHICLES`, and per-type defaults (e.g., `NUMBER_OF_CARS`, `NUMBER_OF_TRUCKS`, `NUUMBER_OF_BUSES`).
- Passenger limits: `MAX_CAR_PASSENGER`, `MAX_BUS_PASSENGER`, `MAX_TRUCK_PASSENGER`.
- Speed: `SPEED_OF_VEHICLES` (ms sleep / pacing).
- Path/records/logs locations: `PATH_ON_MAP`, `RESULTS_DIRECTORY`, `LIST_OF_PUNISHED_PERSONS_DIRECTORY`, `LIST_OF_PUNISHED_VEHICLES_DIRECTORY`, `TERMINALS_DIRECTORY`.
- UI colors/labels (e.g., vehicle color codes).

After changing constants, rebuild and re-run.

---

## Project layout

```
Java-Projekat-2023/
├─ pom.xml
├─ mvnw, mvnw.cmd
├─ src/
│  └─ main/
│     ├─ java/
│     │  ├─ granicni_prelaz/javaprojekat2023/HelloApplication.java
│     │  ├─ granicni_prelaz/javaprojekat2023/constants/Constants.java
│     │  ├─ granicni_prelaz/javaprojekat2023/controllers/...
│     │  ├─ granicni_prelaz/javaprojekat2023/simulation/Simulation.java
│     │  ├─ granicni_prelaz/javaprojekat2023/map/{Field, PathOnMap}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/terminals/{Terminal, PoliceTerminal, CustomsTerminal, ...}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/vehicles/{Vehicle, Car, Bus, Truck, PositionEnum}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/persons/{Person, Driver, Passenger}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/gadgets/{IdentificationDocument, Suitcase, CustomsDocumentation}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/incident/{IncidentUtil, ListOfPunishedPersons}.java
│     │  ├─ granicni_prelaz/javaprojekat2023/json/PathJsonParser.java
│     │  └─ granicni_prelaz/javaprojekat2023/util/{SimulationLogger, TerminalWatcher, Utils, TimeCounter}.java
│     └─ resources/
│        └─ granicni_prelaz/javaprojekat2023/*.fxml
├─ res/
│  ├─ json/path.json
│  └─ logs/                      # created at runtime
└─ Records/
   ├─ PoliceRecords/             # created at runtime
   └─ CustomsRecords/            # created at runtime
```

---

## Troubleshooting

- **JavaFX runtime error (class not found / IllegalAccess)**  
  Ensure you are using **JDK 17** and run through Maven (`javafx-controls` and `javafx-fxml` are pulled by `pom.xml`).

- **Path or file errors**  
  Run from the **project root** so relative paths like `res/json/path.json` resolve. The app creates output folders if missing.

- **Nothing appears in records**  
  Run the simulation long enough for incidents to occur; check `res/logs/` for details.

- **UI freezes / doesn’t update**  
  The simulation uses threads and JavaFX Platform calls; make sure you do not block the JavaFX Application Thread with extra code.

---
