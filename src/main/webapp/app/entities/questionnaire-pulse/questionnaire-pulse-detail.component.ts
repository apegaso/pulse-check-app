import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionnairePulse } from 'app/shared/model/questionnaire-pulse.model';

@Component({
    selector: 'jhi-questionnaire-pulse-detail',
    templateUrl: './questionnaire-pulse-detail.component.html'
})
export class QuestionnairePulseDetailComponent implements OnInit {
    questionnaire: IQuestionnairePulse;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionnaire }) => {
            this.questionnaire = questionnaire;
        });
    }

    previousState() {
        window.history.back();
    }
}
