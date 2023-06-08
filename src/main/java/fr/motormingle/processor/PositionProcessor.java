package fr.motormingle.processor;

import fr.motormingle.model.Encounter;
import fr.motormingle.model.Position;
import fr.motormingle.model.Status;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PositionProcessor implements ItemProcessor<List<Position>, List<Encounter>> {

    @Override
    public List<Encounter> process(@NotNull List<Position> items) throws Exception {
        List<Encounter> encounters = new ArrayList<>();
        items.forEach((item) -> {
            Encounter encounter = new Encounter();
            encounter.setUserId1(item.getUserId());
            encounter.setUserId2(item.getUserId());
            encounter.setHash("8a283082a677fff");
            encounter.setDate(items.get(0).getDate().toLocalDate().plusDays(10L));
            encounter.setCount(1);
            encounter.setStatus(Status.PENDING.toString());

            encounters.add(encounter);
        });
        System.err.println(encounters);

        return encounters.size() == 0 ? null : encounters;
    }

}
