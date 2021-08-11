package firsov.study.securitySpring.rest;

import firsov.study.securitySpring.model.Worker;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/workers/")
public class WorkerRestController {

    private List<Worker> WORKERS = Stream.of(
            new Worker(1L, "Ivan", "Ivanov"),
            new Worker(2L, "Petya", "Sidorov"),
            new Worker(3L, "Nikolai", "Bolshou")
    ).collect(Collectors.toList());

    @GetMapping
    @PreAuthorize("hasAuthority('worker:read')")
    public List<Worker> getAll() { return WORKERS; }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('worker:read')")
    public Worker getById(@PathVariable Long id) {
        return WORKERS.stream().filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('worker:write')")
    public Worker create(@RequestBody Worker worker) {
        this.WORKERS.add(worker);
        return worker;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('worker:write')")
    public void deleteById(@PathVariable Long id) {
        this.WORKERS.removeIf(w -> w.getId().equals(id));
    }
}
