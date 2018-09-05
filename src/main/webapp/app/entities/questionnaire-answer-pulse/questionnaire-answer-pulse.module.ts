import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    QuestionnaireAnswerPulseComponent,
    QuestionnaireAnswerPulseDetailComponent,
    QuestionnaireAnswerPulseUpdateComponent,
    QuestionnaireAnswerPulseDeletePopupComponent,
    QuestionnaireAnswerPulseDeleteDialogComponent,
    questionnaireAnswerRoute,
    questionnaireAnswerPopupRoute
} from './';

const ENTITY_STATES = [...questionnaireAnswerRoute, ...questionnaireAnswerPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionnaireAnswerPulseComponent,
        QuestionnaireAnswerPulseDetailComponent,
        QuestionnaireAnswerPulseUpdateComponent,
        QuestionnaireAnswerPulseDeleteDialogComponent,
        QuestionnaireAnswerPulseDeletePopupComponent
    ],
    entryComponents: [
        QuestionnaireAnswerPulseComponent,
        QuestionnaireAnswerPulseUpdateComponent,
        QuestionnaireAnswerPulseDeleteDialogComponent,
        QuestionnaireAnswerPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppQuestionnaireAnswerPulseModule {}
