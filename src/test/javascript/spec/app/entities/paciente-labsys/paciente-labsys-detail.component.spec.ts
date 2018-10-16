/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { PacienteLabsysDetailComponent } from 'app/entities/paciente-labsys/paciente-labsys-detail.component';
import { PacienteLabsys } from 'app/shared/model/paciente-labsys.model';

describe('Component Tests', () => {
    describe('PacienteLabsys Management Detail Component', () => {
        let comp: PacienteLabsysDetailComponent;
        let fixture: ComponentFixture<PacienteLabsysDetailComponent>;
        const route = ({ data: of({ paciente: new PacienteLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [PacienteLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PacienteLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PacienteLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paciente).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
