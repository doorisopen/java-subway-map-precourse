package subway.service;

import subway.domain.*;
import subway.utils.InputValidation;

import java.util.Scanner;

import static subway.view.InputView.*;
import static subway.view.OutputView.printAddLineStationSuccessMessage;
import static subway.view.OutputView.printDeleteLineStationSuccessMessage;

public class LineStationService extends InputValidation {

    public void selectLineStationManagementMenu(Scanner scanner, String menu, LineStationRepository lineStation) {
        if (menu.equals(MenuType.LINE_STATION_ADD.getKey())) {
            addLineStation(scanner, lineStation);
        }
        if (menu.equals(MenuType.LINE_STATION_DELETE.getKey())) {
            deleteLineStation(scanner, lineStation);
        }
        if (menu.equals(MenuType.LINE_STATION_SEARCH.getKey())) {
            printLines();
        }
    }

    private void addLineStation(Scanner scanner, LineStationRepository lineStation) {
        inputAddLineNameRequestMessage();
        String lineName = scanner.nextLine();
        inputAddStartStationNameRequestMessage();
        String startStationName = scanner.nextLine();
        inputAddEndStationNameRequestMessage();
        String endStationName = scanner.nextLine();
        //validation
        validateNameLengthIsMoreThan2(lineName);
        //2. 중복된 노선 이름이 존재하는지 검증한다
        validateStationNameIsContains(startStationName);
        validateStationNameIsContains(endStationName);
        Line line = new Line(lineName);
        LineRepository.addLine(line);
        lineStation.addLineStation(line, StationRepository.findStation(startStationName).get());
        lineStation.addLineStation(line, StationRepository.findStation(endStationName).get());
        printAddLineStationSuccessMessage();
    }

    private void deleteLineStation(Scanner scanner, LineStationRepository lineStation) {
        inputDeleteLineNameRequestMessage();
        String lineName = scanner.nextLine();
        //validation
        //1.입력한 노선 이름이 존재하는지 검증한다
        lineStation.deleteLineStation(LineRepository.findLine(lineName).get());
        LineRepository.deleteLineByName(lineName);
        printDeleteLineStationSuccessMessage();
    }

    private void printLines() {
        LineRepository.printLines();
    }
}