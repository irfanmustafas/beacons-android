package cmpe295.sjsu.edu.salesman;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;

import cmpe295.sjsu.edu.salesman.utils.TileViewActivity;


public class NavigationActivity extends TileViewActivity {

    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );

        // multiple references
        TileView tileView = getTileView();

        // size of original image at 100% scale
        tileView.setSize( 6220, 11060  );

        // detail levels
        tileView.addDetailLevel( 1.000f,"tiles_1/floor/1000/%col%_%row%.jpg", "samples_1/floor_plan.jpg");
        tileView.addDetailLevel( 0.500f, "tiles_1/floor/500/%col%_%row%.png", "samples_1/retailmap.png");
        tileView.addDetailLevel( 0.250f, "tiles_1/floor/250/%col%_%row%.png", "samples_1/retailmap.png");
        tileView.addDetailLevel( 0.125f, "tiles_1/floor/125/%col%_%row%.jpg", "samples_1/retailmap.png");

       // tileView.addDetailLevel( 1.000f, "tiles/plans/1000/%col%_%row%.jpg", "samples/plans.JPG");
     //   tileView.addDetailLevel( 0.500f, "tiles/plans/500/%col%_%row%.jpg", "samples/plans.JPG");
      //  tileView.addDetailLevel( 0.250f, "tiles/plans/250/%col%_%row%.jpg", "samples/plans.JPG");
       // tileView.addDetailLevel( 0.125f, "tiles/plans/125/%col%_%row%.jpg", "samples/plans.JPG");

        // let's use 0-1 positioning...
        tileView.defineRelativeBounds( 0, 0, 1,  1 );

        // center markers along both axes
        tileView.setMarkerAnchorPoints( -0.5f, -0.5f );

        // add a marker listener
        tileView.addMarkerEventListener( markerEventListener );

        // add some pins...
        addPin( 0.25, 0.25 );
        addPin( 0.25, 0.75 );
        addPin( 0.75, 0.25 );
        addPin( 0.75, 0.75 );
        addPin( 0.50, 0.50 );

        // scale it down to manageable size
        tileView.setScale( 0.5 );

        // center the frame
        frameTo( 0.5, 0.5 );

    }

    private void addPin( double x, double y ) {
        ImageView imageView = new ImageView( this );
        imageView.setImageResource(R.drawable.marker_icon );
        getTileView().addMarker( imageView, x, y );
    }

    private MarkerEventListener markerEventListener = new MarkerEventListener() {
        @Override
        public void onMarkerTap( View v, int x, int y ) {
            Toast.makeText(getApplicationContext(), "You tapped a pin", Toast.LENGTH_LONG).show();
        }
    };
}

