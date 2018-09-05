/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnaireAnswerPulseDeleteDialogComponent } from 'app/entities/questionnaire-answer-pulse/questionnaire-answer-pulse-delete-dialog.component';
import { QuestionnaireAnswerPulseService } from 'app/entities/questionnaire-answer-pulse/questionnaire-answer-pulse.service';

describe('Component Tests', () => {
    describe('QuestionnaireAnswerPulse Management Delete Component', () => {
        let comp: QuestionnaireAnswerPulseDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionnaireAnswerPulseDeleteDialogComponent>;
        let service: QuestionnaireAnswerPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnaireAnswerPulseDeleteDialogComponent]
            })
                .overrideTemplate(QuestionnaireAnswerPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionnaireAnswerPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionnaireAnswerPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
