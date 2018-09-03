import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';
import { Principal } from 'app/core';
import { QuestionnairePulseService } from './questionnaire-pulse.service';

@Component({
    selector: 'jhi-questionnaire-pulse',
    templateUrl: './questionnaire-pulse.component.html'
})
export class QuestionnairePulseComponent implements OnInit, OnDestroy {
    questionnaires: IQuestionnairePulse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private questionnaireService: QuestionnairePulseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.questionnaireService.query().subscribe(
            (res: HttpResponse<IQuestionnairePulse[]>) => {
                this.questionnaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuestionnaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IQuestionnairePulse) {
        return item.id;
    }

    registerChangeInQuestionnaires() {
        this.eventSubscriber = this.eventManager.subscribe('questionnaireListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
