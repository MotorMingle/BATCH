package fr.motormingle.processor;

import com.uber.h3core.H3Core;
import fr.motormingle.entity.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PositionProcessor implements ItemProcessor<List<Position>, List<Encounter>> {

    @Override
    public List<Encounter> process(List<Position> items) throws Exception {
        List<Encounter> encounters = new ArrayList<>();
        Map<String, List<Position>> h3Positions = new HashMap<>();
        H3Core h3 = H3Core.newInstance();

        items.forEach((item) -> {
            String h3Index = h3.latLngToCellAddress(item.getLatitude(), item.getLongitude(), 8);
            if (h3Positions.containsKey(h3Index)) {
                h3Positions.get(h3Index).add(item);
            } else {
                List<Position> positions = new ArrayList<>();
                positions.add(item);
                h3Positions.put(h3Index, positions);
            }
        });

        h3Positions.forEach((h3Index, positions) -> {
            if (positions.size() < 2) {
                return;
            }
            for (int i = 0; i < positions.size() - 1; i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    Position position1 = positions.get(i);
                    Position position2 = positions.get(j);
                    Encounter encounter = new Encounter();
                    UserPair userPair = new UserPair();
                    userPair.setUserId1(position1.getUser().getId());
                    userPair.setUserId2(position2.getUser().getId());
                    encounter.setId(userPair);
                    encounter.setUserId1(position1.getUser());
                    encounter.setUserId2(position2.getUser());
                    encounter.setHash(h3Index);
                    UserPairStats userPairStats = new UserPairStats();
                    userPairStats.setUser1Status(EncounterStatus.PENDING);
                    userPairStats.setUser2Status(EncounterStatus.PENDING);
                    userPairStats.setDate(position1.getId().getDate().toLocalDate());
                    encounter.setUserPairStats(userPairStats);
                    encounters.add(encounter);
                }
            }
        });

        return encounters.size() == 0 ? null : encounters;
    }

}
