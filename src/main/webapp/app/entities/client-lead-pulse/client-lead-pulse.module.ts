import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    ClientLeadPulseComponent,
    ClientLeadPulseDetailComponent,
    ClientLeadPulseUpdateComponent,
    ClientLeadPulseDeletePopupComponent,
    ClientLeadPulseDeleteDialogComponent,
    clientLeadRoute,
    clientLeadPopupRoute
} from './';

const ENTITY_STATES = [...clientLeadRoute, ...clientLeadPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientLeadPulseComponent,
        ClientLeadPulseDetailComponent,
        ClientLeadPulseUpdateComponent,
        ClientLeadPulseDeleteDialogComponent,
        ClientLeadPulseDeletePopupComponent
    ],
    entryComponents: [
        ClientLeadPulseComponent,
        ClientLeadPulseUpdateComponent,
        ClientLeadPulseDeleteDialogComponent,
        ClientLeadPulseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppClientLeadPulseModule {}
