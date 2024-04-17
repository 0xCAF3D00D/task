import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";
import {ProductDto} from "../generated/api/models/product-dto";
import {ProductControllerService} from "../generated/api/services/product-controller.service";
import {PurchaseControllerService} from "../generated/api/services/purchase-controller.service";
import {HttpClientModule} from "@angular/common/http";
import {ApiModule} from "../generated/api/api.module";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    HttpClientModule,
    ApiModule,
    CurrencyPipe,
    NgForOf,
    NgIf
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  errorMessage: string | null = null;
  products: ProductDto[] = [];

  constructor(private productsService: ProductControllerService,
              private purchaseService: PurchaseControllerService) {
  }

  ngOnInit() {
    this.productsService.getProducts().subscribe({
      next: (data) => {
        this.products = data.data
        console.log(data)
      },
      error: (err) => {
        console.error('Failed to get products', err);
        this.errorMessage = 'Failed to load products';
      }
    });
  }

  buyProduct(productId: string) {
    console.log('Attempting to buy product', productId);
    this.purchaseService.createPurchase({
      body: {
        productId: productId,
        email: "test@test.com",
        redirectUrl: "http://localhost:4200/purchase/"
      }
    }).subscribe({
      next: (response) => {
        if (response.status == 'OK') {
          if (response.data.status == 'PENDING' && response.data.url) {
            console.log('Purchase successful', response);

            window.location.href = response.data.url;
            return
          }
        }

        this.errorMessage = 'Failed to purchase product';
      },
      error: (err) => {
        this.errorMessage = 'Failed to purchase product';
        console.error('Purchase failed', err);
      }
    });
  }

}
