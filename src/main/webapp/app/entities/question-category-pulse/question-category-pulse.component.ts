import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';
import { Principal } from 'app/core';
import { QuestionCategoryPulseService } from './question-category-pulse.service';

@Component({
    selector: 'jhi-question-category-pulse',
    templateUrl: './question-category-pulse.component.html'
})
export class QuestionCategoryPulseComponent implements OnInit, OnDestroy {
    questionCategories: IQuestionCategoryPulse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private questionCategoryService: QuestionCategoryPulseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.questionCategoryService.query().subscribe(
            (res: HttpResponse<IQuestionCategoryPulse[]>) => {
                this.questionCategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuestionCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IQuestionCategoryPulse) {
        return item.id;
    }

    registerChangeInQuestionCategories() {
        this.eventSubscriber = this.eventManager.subscribe('questionCategoryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
