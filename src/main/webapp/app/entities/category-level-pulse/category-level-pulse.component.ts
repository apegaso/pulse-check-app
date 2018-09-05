import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';
import { Principal } from 'app/core';
import { CategoryLevelPulseService } from './category-level-pulse.service';

@Component({
    selector: 'jhi-category-level-pulse',
    templateUrl: './category-level-pulse.component.html'
})
export class CategoryLevelPulseComponent implements OnInit, OnDestroy {
    categoryLevels: ICategoryLevelPulse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private categoryLevelService: CategoryLevelPulseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.categoryLevelService.query().subscribe(
            (res: HttpResponse<ICategoryLevelPulse[]>) => {
                this.categoryLevels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCategoryLevels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategoryLevelPulse) {
        return item.id;
    }

    registerChangeInCategoryLevels() {
        this.eventSubscriber = this.eventManager.subscribe('categoryLevelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
