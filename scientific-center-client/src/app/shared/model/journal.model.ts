import { Issue } from './issue.model';

export class Journal {
    id: number;
    name: string;
    openAccess: boolean;
    issues: Issue[];
}