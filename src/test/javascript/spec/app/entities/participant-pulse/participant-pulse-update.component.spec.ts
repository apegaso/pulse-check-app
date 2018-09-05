/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { ParticipantPulseUpdateComponent } from 'app/entities/participant-pulse/participant-pulse-update.component';
import { ParticipantPulseService } from 'app/entities/participant-pulse/participant-pulse.service';
import { ParticipantPulse } from 'app/shared/model/participant-pulse.model';

describe('Component Tests', () => {
    describe('ParticipantPulse Management Update Component', () => {
        let comp: ParticipantPulseUpdateComponent;
        let fixture: ComponentFixture<ParticipantPulseUpdateComponent>;
        let service: ParticipantPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [ParticipantPulseUpdateComponent]
            })
                .overrideTemplate(ParticipantPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParticipantPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParticipantPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ParticipantPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.participant = entity;
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
                    const entity = new ParticipantPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.participant = entity;
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
