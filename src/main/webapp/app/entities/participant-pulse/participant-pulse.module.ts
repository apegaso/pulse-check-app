import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    ParticipantPulseComponent,
    ParticipantPulseDetailComponent,
    ParticipantPulseUpdateComponent,
    ParticipantPulseDeletePopupComponent,
    ParticipantPulseDeleteDialogComponent,
    participantRoute,
    participantPopupRoute
} from './';

const ENTITY_STATES = [...participantRoute, ...participantPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParticipantPulseComponent,
        ParticipantPulseDetailComponent,
        ParticipantPulseUpdateComponent,
        ParticipantPulseDeleteDialogComponent,
        ParticipantPulseDeletePopupComponent
    ],
    entryComponents: [
        ParticipantPulseComponent,
        ParticipantPulseUpdateComponent,
        ParticipantPulseDeleteDialogComponent,
        ParticipantPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppParticipantPulseModule {}
