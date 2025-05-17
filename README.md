# Flight Path Finder 

> Command‑line Java tool that enumerates every possible flight path between two U.S. cities and highlights the cheapest or fastest options.

---

## Table of Contents
- [About](#about)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Compilation](#compilation)
  - [Running](#running)
- [Usage](#usage)
- [Limitations](#limitations)
- [Extending the Data](#extending-the-data)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)

---

## About
This program models a flight network as a **directed weighted graph** where each edge carries a cost (USD) and duration (minutes). Given a list of trip requests, it performs **depth‑first search (DFS)** to discover every possible acyclic path between two airports and then sorts the results by either **total cost** or **total travel time**. Finally, it reports a summary of the best paths.

## Features
- Automatic graph construction from a pipe‑delimited data file (`FlightData.txt`).
- All‑paths search using DFS with cumulative cost and time tracking.
- Dual optimisation criteria – choose `C` for cost or `T` for time in the requests file.
- Human‑readable output that lists every path and highlights the optimal choices.
- Easily extensible – just edit the data files; no code changes required.

## Project Structure
```
├── Node.java          // Vertex & edge container
├── Path.java          // Helper to store full routes
├── Graph.java         // Adjacency‑list graph + DFS logic
├── Main.java          // Program entry point
├── FlightData.txt     // Available directed flights (src|dst|cost|time)
├── Input.txt          // Trip requests (src|dst|C|T)
└── README.md          // Project documentation
```

## Getting Started
### Prerequisites
- **Java 11** or newer (tested with OpenJDK 17)
- A terminal / command prompt

### Compilation
```bash
javac *.java
```

### Running
```bash
java Main
```
The program reads **FlightData.txt** and **Input.txt** from the current folder and prints a graph summary followed by the results for each trip request.

---

## Usage
`Input.txt` controls which trips are analysed. Each line uses the format:
```
<SourceCity>|<DestinationCity>|<Option>
```
where `<Option>` is:
- `C` → rank paths by **total cost**
- `T` → rank paths by **total time**

Example:
```
Dallas|Seattle|T
Seattle|Dallas|C
```

Sample (truncated) console output:
```
Flight 1: Dallas, Seattle (Time)
    Path 1: Dallas -> Seattle  Cost: 348, Time: 160
    Path 2: Dallas -> Chicago -> Seattle  Cost: 684, Time: 557
    ...
Path Summary:
    Total number of paths: 5
    Best path by cost: Dallas -> Seattle  Cost: 348, Time: 160
    Best path by time: Dallas -> Seattle  Cost: 348, Time: 160
```

---

## Limitations
This implementation **explores every possible path** between the source and destination. On very large flight datasets with many overlapping routes, the number of possibilities can grow explosively. Because each recursive call consumes stack space, extremely dense graphs may trigger a `StackOverflowError`.

If you need to scale to bigger networks, consider:
- Converting the recursive DFS to an iterative version with an explicit stack.
- Imposing depth or cost limits to prune the search.
- Using algorithms that generate only the *k*-best paths (e.g., Yen’s algorithm).

---

## Extending the Data
### Add or edit flights
1. Open **FlightData.txt**.
2. Each line contains four fields separated by `|`:
   ```
   Source|Destination|Cost|Time
   ```
3. Values are integers (cost in USD, time in minutes). Add reciprocal entries if travel is bidirectional.

### Add new trip requests
1. Open **Input.txt**.
2. Append a new line using the format shown in [Usage](#usage).

---

