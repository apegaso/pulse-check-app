/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { ClientLeadPulseUpdateComponent } from 'app/entities/client-lead-pulse/client-lead-pulse-update.component';
import { ClientLeadPulseService } from 'app/entities/client-lead-pulse/client-lead-pulse.service';
import { ClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';

describe('Component Tests', () => {
    describe('ClientLeadPulse Management Update Component', () => {
        let comp: ClientLeadPulseUpdateComponent;
        let fixture: ComponentFixture<ClientLeadPulseUpdateComponent>;
        let service: ClientLeadPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [ClientLeadPulseUpdateComponent]
            })
                .overrideTemplate(ClientLeadPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientLeadPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientLeadPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClientLeadPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clientLead = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClientLeadPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clientLead = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
