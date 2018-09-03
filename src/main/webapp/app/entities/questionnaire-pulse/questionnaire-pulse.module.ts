import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    QuestionnairePulseComponent,
    QuestionnairePulseDetailComponent,
    QuestionnairePulseUpdateComponent,
    QuestionnairePulseDeletePopupComponent,
    QuestionnairePulseDeleteDialogComponent,
    questionnaireRoute,
    questionnairePopupRoute
} from './';

const ENTITY_STATES = [...questionnaireRoute, ...questionnairePopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionnairePulseComponent,
        QuestionnairePulseDetailComponent,
        QuestionnairePulseUpdateComponent,
        QuestionnairePulseDeleteDialogComponent,
        QuestionnairePulseDeletePopupComponent
    ],
    entryComponents: [
        QuestionnairePulseComponent,
        QuestionnairePulseUpdateComponent,
        QuestionnairePulseDeleteDialogComponent,
        QuestionnairePulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppQuestionnairePulseModule {}
