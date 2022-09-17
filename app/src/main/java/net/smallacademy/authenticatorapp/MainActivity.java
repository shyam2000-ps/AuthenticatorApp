 package net.smallacademy.authenticatorapp;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.lifecycle.Observer;
 import androidx.lifecycle.ViewModelProvider;
 import androidx.navigation.NavController;
 import androidx.navigation.Navigation;
 import androidx.navigation.fragment.NavHostFragment;
 import androidx.navigation.ui.NavigationUI;

 import com.google.android.material.bottomnavigation.BottomNavigationView;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.firestore.FirebaseFirestore;

 import net.smallacademy.authenticatorapp.models.CartItem;
 import net.smallacademy.authenticatorapp.viewmodels.ShopViewModel;

 import java.util.List;


 public class MainActivity extends AppCompatActivity {
     TextView fullName, email, phone;
     ShopViewModel shopViewModel;
     NavController navController;
     FirebaseUser user;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     private int cartQuantity = 0;
       @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           navController = Navigation.findNavController(this, R.id.nav_host_fragment);
           NavigationUI.setupActionBarWithNavController(this, navController);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeMenuFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ngo:
                        navController.navigate(R.id.ngofrg);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home()).commit();
                        break;
                    case R.id.home:
                        navController.navigate(R.id.homefrag);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeMenuFragment()).commit();
                        break;
                    case R.id.shop:
                        navController.navigate(R.id.shopFragment);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new net.smallacademy.authenticatorapp.views.MainActivity()).commit();
//                        startActivity( new Intent( getApplicationContext(), net.smallacademy.authenticatorapp.views.MainActivity.class ) );

                        break;
                }
                return true;
            }
        });

//          navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//          NavigationUI.setupActionBarWithNavController(this, navController);
           shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
           shopViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
               @Override
               public void onChanged (List<CartItem> cartItems) {
                   int quantity = 0;
                   for (CartItem cartItem : cartItems) {
                       quantity += cartItem.getQuantity();
                   }
                   cartQuantity = quantity;
                   invalidateOptionsMenu();
               }
           });
    }

     @Override
     public boolean onSupportNavigateUp() {
         navController.navigateUp();
         return super.onSupportNavigateUp();
     }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sidelist, menu);
        final MenuItem menuItem = menu.findItem(R.id.cartFragment);
        View actionView = menuItem.getActionView();

        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);

        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);

        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }



     public boolean onCreateOptionsMenu(@NonNull MenuItem item) {
         return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
     }


      @Override
    public boolean onOptionsItemSelected ( MenuItem item) {
      switch (item.getItemId()) {
          case R.id.button:
          {
              FirebaseAuth.getInstance().signOut();
              startActivity(new Intent(getApplicationContext(), Login.class));
                     }
          return true;
          case R.id.cartFragment:
          {
             NavigationUI.onNavDestinationSelected(item, navController);
          }
             default:
             return super.onOptionsItemSelected(item);
      }
    }

}

















