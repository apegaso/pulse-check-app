import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PulseCheckAppOrganizationPulseModule } from './organization-pulse/organization-pulse.module';
import { PulseCheckAppEventPulseModule } from './event-pulse/event-pulse.module';
import { PulseCheckAppClientLeadPulseModule } from './client-lead-pulse/client-lead-pulse.module';
import { PulseCheckAppOrgAdminPulseModule } from './org-admin-pulse/org-admin-pulse.module';
import { PulseCheckAppParticipantPulseModule } from './participant-pulse/participant-pulse.module';
import { PulseCheckAppQuestionPulseModule } from './question-pulse/question-pulse.module';
import { PulseCheckAppQuestionCategoryPulseModule } from './question-category-pulse/question-category-pulse.module';
import { PulseCheckAppQuestionnairePulseModule } from './questionnaire-pulse/questionnaire-pulse.module';
import { PulseCheckAppQuestionnaireAnswerPulseModule } from './questionnaire-answer-pulse/questionnaire-answer-pulse.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PulseCheckAppOrganizationPulseModule,
        PulseCheckAppEventPulseModule,
        PulseCheckAppClientLeadPulseModule,
        PulseCheckAppOrgAdminPulseModule,
        PulseCheckAppParticipantPulseModule,
        PulseCheckAppQuestionPulseModule,
        PulseCheckAppQuestionCategoryPulseModule,
        PulseCheckAppQuestionnairePulseModule,
        PulseCheckAppQuestionnaireAnswerPulseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppEntityModule {}
