/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionnairePulseDeleteDialogComponent } from 'app/entities/questionnaire-pulse/questionnaire-pulse-delete-dialog.component';
import { QuestionnairePulseService } from 'app/entities/questionnaire-pulse/questionnaire-pulse.service';

describe('Component Tests', () => {
    describe('QuestionnairePulse Management Delete Component', () => {
        let comp: QuestionnairePulseDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionnairePulseDeleteDialogComponent>;
        let service: QuestionnairePulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionnairePulseDeleteDialogComponent]
            })
                .overrideTemplate(QuestionnairePulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionnairePulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionnairePulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
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
            ));
        });
    });
});
