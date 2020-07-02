package de.fzi.efeu.db.model;

public enum OrderState {
    NEW,
    READYFORPLANNING,
    PLANNED,
    INEXECUTION,
    FINISHED,
    ABORTED,
    DISRUPTED
}
