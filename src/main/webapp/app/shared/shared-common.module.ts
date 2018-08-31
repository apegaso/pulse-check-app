import { NgModule } from '@angular/core';

import { PulseCheckAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PulseCheckAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PulseCheckAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PulseCheckAppSharedCommonModule {}
