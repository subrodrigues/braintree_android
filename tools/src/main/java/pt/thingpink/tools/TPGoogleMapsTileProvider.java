package pt.thingpink.tools;

import android.graphics.Rect;
import android.util.SparseArray;

import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class TPGoogleMapsTileProvider extends UrlTileProvider {

	private String mMapIdentifier;
	private String mapTileProviderFormat;
	public static SparseArray<Rect> TILE_ZOOMS;

	public TPGoogleMapsTileProvider(String mapIdentifier, String mapTileProviderFormat) {
		super(256, 256);

		this.mMapIdentifier = mapIdentifier;
		this.mapTileProviderFormat = mapTileProviderFormat;
	}

	@Override
	public URL getTileUrl(int x, int y, int z) {
		try {

			if (hasTile(x, y, z)) {
				// Log.i(Globals.TAG_APPNAME,
				// String.format(Globals.MAP_TILE_PROVIDER_FORMAT, z, x, y));
				return new URL(String.format(mapTileProviderFormat, z, x, y));
			} else {
				return null;
			}

		} catch (MalformedURLException e) {
			return null;
		}
	}

	private boolean hasTile(int x, int y, int zoom) {
		Rect b = TILE_ZOOMS.get(zoom);
		return b == null ? false
				: (b.left <= x && x <= b.right && b.top <= y && y <= b.bottom);
	}

	// private static final SparseArray<Rect> TILE_ZOOMS = new
	// SparseArray<Rect>() {
	// {
	// put(13, new Rect(3897, 3172, 3897, 3172));
	// put(14, new Rect(7794, 6345, 7794, 6345));
	// put(15, new Rect(15588, 12690, 15589, 12691));
	// put(16, new Rect(31177, 25381, 31179, 25383));
	// put(17, new Rect(62355, 50762, 62359, 50766));
	// put(18, new Rect(124711, 101524, 124719, 101533));
	// put(19, new Rect(249422, 203048, 249439, 203067));
	// put(20, new Rect(498844, 406097, 498879, 406135));
	// }
	// };
}
