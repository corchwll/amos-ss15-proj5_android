/*
 *     Mobile Time Accounting
 *     Copyright (C) 2015
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;

public class AddProjectMapActivity extends AppCompatActivity
{
	private static final long minTime = 10000L;
	private static final float minDistance = 500.0f;

	private MapFragment mapFragment;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Marker marker;

	/**
	 * This method is called in the android lifecycle when the activity is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_project_map);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		initMapFragment();
	}

	/**
	 * This method initializes the toolbar.
	 *
	 * methodtype initialization method
	 */
	private void initToolbar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	/**
	 * This method initializes the map fragment. Sets a first Marker on the map. Sets onMapClickListener to replace
	 * the marker
	 *
	 * methodtype initialization method
	 */
	private void initMapFragment()
	{
		mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(new OnMapReadyCallback()
		{
			@Override
			public void onMapReady(final GoogleMap googleMap)
			{
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener()
				{
					@Override
					public boolean onMyLocationButtonClick()
					{
						initLocation();
						return false;
					}
				});

				TypedValue latValue = new TypedValue();
				getResources().getValue(R.dimen.lat, latValue, true);
				float lat = latValue.getFloat();

				TypedValue lngValue = new TypedValue();
				getResources().getValue(R.dimen.lng, lngValue, true);
				float lng = lngValue.getFloat();

				marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
																.title(getResources().getString(R.string.marker)));
				mapFragment.getMap()
						   .moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

				googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
				{
					@Override
					public void onMapClick(LatLng latLng)
					{
						replaceMarker(latLng);
					}
				});
			}

		});
	}

	/**
	 * This method removes the marker and sets a new marker to the given LatLng
	 *
	 * @param latLng defines the position of the new marker
	 * methodtype command method
	 */
	protected void replaceMarker(LatLng latLng)
	{
		marker.remove();
		GoogleMap map = mapFragment.getMap();
		if(map != null)
		{
			marker = mapFragment.getMap().addMarker(new MarkerOptions().position(latLng));
			mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLng(latLng));
		}
	}

	/**
	 * This method initializes LocationManager and LocationListener. Sets the LocationListener to LocationManager
	 *
	 * methodtype initialization method
	 */
	private void initLocation()
	{
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener()
		{
			@Override
			public void onLocationChanged(Location location)
			{
				replaceMarker(new LatLng(location.getLatitude(), location.getLongitude()));
			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle)
			{
			}

			@Override
			public void onProviderEnabled(String s)
			{
			}

			@Override
			public void onProviderDisabled(String s)
			{
				showNoGPSEnabledToast();
			}
		};

		if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance,
					locationListener);
		}
		if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
		}
	}

	//todo replace this with a dialog
	/**
	 * Shows a toast to show that no GPS is available
	 *
	 * methodtype helper method
	 */
	private void showNoGPSEnabledToast()
	{
		Toast.makeText(this, getResources().getString(R.string.noGPSWarning), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Called as part of the activity lifecycle when the activity is going into the background, but has not been killed.
	 *
	 * methodtype initialization method
	 */
	@Override
	protected void onPause()
	{
		removeLocationListener();
		super.onPause();
	}

	/**
	 * Removes the LocationListener from LocationManager to safe battery.
	 *
	 * methodtype command method
	 */
	public void removeLocationListener()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(locationListener);
			mapFragment.getMap().setMyLocationEnabled(false);
		}
	}

	/**
	 * Called when the activity has detected the user's press of the back key.
	 *
	 * methodtype command method
	 */
	@Override
	public void onBackPressed()
	{
		finishActivityAndShowAnimation();
	}

	/**
	 * Finish the activity and shows an animation
	 *
	 * methodtype command method
	 */
	private void finishActivityAndShowAnimation()
	{
		super.finish();
		overridePendingTransition(R.animator.empty_animator, R.animator.fade_out_right);
	}

	/**
	 * This method is called in the android lifecycle when a menu is created.
	 *
	 * @param menu the menu item which has to be created
	 * methodtype initialization method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_add, menu);
		return true;
	}

	/**
	 * This method is called in the android lifecycle when a menu item is clicked on.
	 *
	 * @param item the item which was targeted
	 * @return true if there was an item clicked
	 * methodtype boolean query method
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				finishActivityAndShowAnimation();
				return true;
			case R.id.action_save_new_item:
					finishActivity();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Finishes the activity. Put latitude and longitude to intent.
	 *
	 * methodtype command method
	 */
	private void finishActivity()
	{
		if(marker != null)
		{
			Intent resultIntent = new Intent();
			resultIntent.putExtra("lat", marker.getPosition().latitude);
			resultIntent.putExtra("lng", marker.getPosition().longitude);
			setResult(Activity.RESULT_OK, resultIntent);
		}
		finishActivityAndShowAnimation();
	}
}
