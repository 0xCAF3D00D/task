import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription, interval, timer} from 'rxjs';
import {switchMap} from 'rxjs/operators';
import {PurchaseControllerService} from "../generated/api/services/purchase-controller.service";
import {HttpClientModule} from "@angular/common/http";
import {ApiModule} from "../generated/api/api.module";
import {PurchaseDto} from "../generated/api/models/purchase-dto";
import {ApiDataResponsePurchaseDto} from "../generated/api/models/api-data-response-purchase-dto";
import {CurrencyPipe, NgIf} from "@angular/common";

@Component({
  selector: 'app-purchase-status',
  templateUrl: './purchase-status.component.html',
  standalone: true,
  imports: [
    HttpClientModule,
    ApiModule,
    CurrencyPipe,
    NgIf
  ],
  styleUrls: ['./purchase-status.component.css'],
})
export class PurchaseStatusComponent implements OnInit, OnDestroy {
  errorMessage: string | null = null;
  purchase!: PurchaseDto;
  private updateSubscription!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private purchaseService: PurchaseControllerService
  ) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      console.error('Purchase ID is required');
      return;
    }

    this.updateSubscription = timer(0, 5000).pipe(
      switchMap(() => this.purchaseService.getPurchase({id: id}))
    ).subscribe({
      next: (response: ApiDataResponsePurchaseDto) => {
        if(response.status !== 'OK') {
          this.errorMessage = 'Failed to load purchase with id ' + id;
          console.error('Failed to fetch purchase details:', response)
          return;
        }

        this.purchase = response.data;
        if (['SUCCESS', 'DECLINE', 'ERROR'].includes(this.purchase.status)) {
          this.updateSubscription.unsubscribe();
        }
      },
      error: (err) => {
        this.errorMessage = 'Failed to load purchase with id ' + id;
        console.error('Failed to fetch purchase details:', err)
      }
    });
  }

  ngOnDestroy(): void {
    if (this.updateSubscription) {
      this.updateSubscription.unsubscribe();
    }
  }

  getStatusMessage(status: string): string {
    switch (status) {
      case 'CREATED':
        return 'Your purchase has been created and is awaiting payment.';
      case 'PENDING':
        return 'Your purchase is pending, please complete the payment.';
      case 'SUCCESS':
        return 'Thank you! Your purchase was successful.';
      case 'DECLINE':
        return 'Your purchase was declined. Please try again.';
      case 'ERROR':
        return 'An error occurred with your purchase. Please contact support.';
      default:
        return 'Checking purchase status...';
    }
  }
}
