import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PulseCheckAppSharedModule } from 'app/shared';
import {
    EventPulseComponent,
    EventPulseDetailComponent,
    EventPulseUpdateComponent,
    EventPulseDeletePopupComponent,
    EventPulseDeleteDialogComponent,
    eventRoute,
    eventPopupRoute
} from './';

const ENTITY_STATES = [...eventRoute, ...eventPopupRoute];

@NgModule({
    imports: [PulseCheckAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EventPulseComponent,
        EventPulseDetailComponent,
        EventPulseUpdateComponent,
        EventPulseDeleteDialogComponent,
        EventPulseDeletePopupComponent
    ],
    entryComponents: [EventPulseComponent, EventPulseUpdateComponent, EventPulseDeleteDialogComponent, EventPulseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PulseCheckAppEventPulseModule {}
