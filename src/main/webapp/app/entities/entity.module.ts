import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PulseCheckAppOrganizationPulseModule } from './organization-pulse/organization-pulse.module';
import { PulseCheckAppEventPulseModule } from './event-pulse/event-pulse.module';
import { PulseCheckAppClientLeadPulseModule } from './client-lead-pulse/client-lead-pulse.module';
import { PulseCheckAppOrgAdminPulseModule } from './org-admin-pulse/org-admin-pulse.module';
import { PulseCheckAppParticipantPulseModule } from './participant-pulse/participant-pulse.module';
import { PulseCheckAppQuestionPulseModule } from './question-pulse/question-pulse.module';
import { PulseCheckAppCategoryPulseModule } from './category-pulse/category-pulse.module';
import { PulseCheckAppCategoryLevelPulseModule } from './category-level-pulse/category-level-pulse.module';
import { PulseCheckAppQuestionnairePulseModule } from './questionnaire-pulse/questionnaire-pulse.module';
import { PulseCheckAppQuestionnaireAnswerPulseModule } from './questionnaire-answer-pulse/questionnaire-answer-pulse.module';
import { PulseCheckAppUserExtPulseModule } from './user-ext-pulse/user-ext-pulse.module';
import { PulseCheckAppEmailModule } from './email/email.module';
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
        PulseCheckAppCategoryPulseModule,
        PulseCheckAppCategoryLevelPulseModule,
        PulseCheckAppQuestionnairePulseModule,
        PulseCheckAppQuestionnaireAnswerPulseModule,
        PulseCheckAppUserExtPulseModule,
        PulseCheckAppEmailModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppEntityModule {}
