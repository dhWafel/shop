package pl.example.shop.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;


@Service
public class FileService implements Runnable{
    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("/home/fernus/batch");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watchKey;

            while ((watchKey = watchService.take())!=null){
                watchKey.pollEvents().forEach(watchEvent -> {
                    Path filePath = Paths.get(path.toString(), watchEvent.context().toString());
                    if(Files.isRegularFile(filePath)){
                        try {
                            Files.lines(filePath).forEach(System.out::println);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println(watchEvent.context().toString());
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
