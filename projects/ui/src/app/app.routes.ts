import { Routes } from '@angular/router';
import {PurchaseStatusComponent} from "./purchase-status/purchase-status.component";
import {ProductListComponent} from "./product-list/product-list.component";

export const routes: Routes = [
  { path: 'purchase/:id', component: PurchaseStatusComponent },
  { path: 'products', component: ProductListComponent },
  { path: '', redirectTo: '/products', pathMatch: 'full' } // Default route
];
