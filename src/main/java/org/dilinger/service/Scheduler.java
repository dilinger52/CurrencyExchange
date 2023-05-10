package org.dilinger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {
    @Autowired
    private final MonoManager monoManager;
    @Autowired
    private final NBUManager nbuManager;
    @Autowired
    private final PrivateManager privateManager;

    public Scheduler(MonoManager monoManager, NBUManager nbuManager, PrivateManager privateManager) {
        this.monoManager = monoManager;
        this.nbuManager = nbuManager;
        this.privateManager = privateManager;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCurrencyExchange() {
        monoManager.getTodayCurrencyExchange();
        nbuManager.getTodayCurrencyExchange();
        privateManager.getTodayCurrencyExchange();
    }
}
