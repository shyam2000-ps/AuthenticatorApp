package net.smallacademy.authenticatorapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.models.CartItem;
import net.smallacademy.authenticatorapp.viewmodels.ShopViewModel;

import java.util.List;

public class MainActivity extends Fragment {

    private static final String TAG = "MainActivity";
    NavController navController;
    ShopViewModel shopViewModel;
    Menu menu;
    MenuItem item;
    MenuInflater menuinflater;
    private int cartQuantity = 0;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view;
        view = inflater.inflate(R.layout.activity_shop, container, false);
        navController = Navigation.findNavController(view);
        //NavigationUI.setupActionBarWithNavController(new net.smallacademy.authenticatorapp.MainActivity(), navController);
//        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
//        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
//            @Override
//            public void onChanged (List<CartItem> cartItems) {
//                int quantity = 0;
//                for (CartItem cartItem : cartItems) {
//                    quantity += cartItem.getQuantity();
//                }
//                cartQuantity = quantity;
//                getActivity().invalidateOptionsMenu();
//            }
//        });

        return view;
    }
//    @Override
//    public boolean onSupportNavigateUp( ) {
//        navController.navigateUp();
//        return super.onSupportNavigateUp();
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Your menu needs to be added here
//        getActivity().getMenuInflater().inflate(R.menu.sidelist, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.cart);
//        View actionView = menuItem.getActionView();
//
//        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);
//
//        cartBadgeTextView.setText(String.valueOf(cartQuantity));
//        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//    }
}