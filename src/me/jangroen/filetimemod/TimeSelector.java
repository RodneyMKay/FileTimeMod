package me.jangroen.filetimemod;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.IntStream;

public class TimeSelector extends JPanel {
    private static final int FROM_YEAR = 1970;
    private static final int TO_YEAR = 2051;

    private JComboBox<String> year;
    private JComboBox<String> month;
    private JComboBox<String> day;

    private JComboBox<String> hour;
    private JComboBox<String> minute;

    public TimeSelector() {
        setLayout(new FlowLayout());
        LocalDateTime now = LocalDateTime.now();

        this.year = new JComboBox<>(IntStream.range(FROM_YEAR, TO_YEAR)
                .mapToObj(String::valueOf).toArray(String[]::new));
        this.year.setSelectedItem(String.valueOf(now.get(ChronoField.YEAR)));

        this.month = new JComboBox<>(Arrays.stream(Month.values())
                .map(m -> m.getDisplayName(TextStyle.FULL, Locale.ENGLISH)).toArray(String[]::new));
        this.month.setSelectedIndex(now.get(ChronoField.MONTH_OF_YEAR) - 1);

        this.day = new JComboBox<>(IntStream.range(1, getAvailableDays() + 1)
                .mapToObj(String::valueOf).toArray(String[]::new));
        this.day.setSelectedIndex(now.get(ChronoField.DAY_OF_MONTH) - 1);

        this.hour = new JComboBox<>(IntStream.range(0, 24).mapToObj(String::valueOf).toArray(String[]::new));
        this.hour.setSelectedIndex(now.get(ChronoField.HOUR_OF_DAY));

        this.minute = new JComboBox<>(IntStream.range(0, 60).mapToObj(String::valueOf).toArray(String[]::new));
        this.minute.setSelectedIndex(now.get(ChronoField.MINUTE_OF_HOUR));

        add(this.day);
        add(this.month);
        add(this.year);
        add(new JLabel("     "));
        add(this.hour);
        add(new JLabel(":"));
        add(this.minute);
    }

    private int getAvailableDays() {
        return YearMonth.of(year.getSelectedIndex() + 1970, month.getSelectedIndex() + 1).lengthOfMonth();
    }

    public LocalDateTime getTime() {
        return LocalDateTime.of(year.getSelectedIndex() + FROM_YEAR, month.getSelectedIndex() + 1,
                day.getSelectedIndex() + 1, hour.getSelectedIndex(), minute.getSelectedIndex());
    }
}
