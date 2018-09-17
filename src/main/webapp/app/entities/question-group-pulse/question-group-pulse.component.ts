import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionGroupPulse } from 'app/shared/model/question-group-pulse.model';
import { Principal } from 'app/core';
import { QuestionGroupPulseService } from './question-group-pulse.service';

@Component({
    selector: 'jhi-question-group-pulse',
    templateUrl: './question-group-pulse.component.html'
})
export class QuestionGroupPulseComponent implements OnInit, OnDestroy {
    questionGroups: IQuestionGroupPulse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private questionGroupService: QuestionGroupPulseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.questionGroupService.query().subscribe(
            (res: HttpResponse<IQuestionGroupPulse[]>) => {
                this.questionGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuestionGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IQuestionGroupPulse) {
        return item.id;
    }

    registerChangeInQuestionGroups() {
        this.eventSubscriber = this.eventManager.subscribe('questionGroupListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
