package com.bono.zero.test;

import com.bono.zero.SettingsInitializer;
import com.bono.zero.api.Endpoint;
import com.bono.zero.model.Settings;

import java.util.concurrent.*;

/**
 * Created by hendriknieuwenhuis on 26/07/15.
 */
public class TestSettingsInitializer {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Settings> settingsFuture = executorService.submit(new SettingsInitializer(new Endpoint(), new Settings()));
        Settings settings = null;
        try {
            settings = settingsFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(settings.toString());
        executorService.shutdown();
    }
}
