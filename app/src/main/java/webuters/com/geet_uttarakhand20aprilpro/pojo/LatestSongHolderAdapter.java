package webuters.com.geet_uttarakhand20aprilpro.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 16-02-2018.
 */

public class LatestSongHolderAdapter {
    List<LatestSongPOJO> latestSongPOJOS=new ArrayList<>();

    public List<LatestSongPOJO> getLatestSongPOJOS() {
        return latestSongPOJOS;
    }

    public void setLatestSongPOJOS(List<LatestSongPOJO> latestSongPOJOS) {
        this.latestSongPOJOS = latestSongPOJOS;
    }
}
